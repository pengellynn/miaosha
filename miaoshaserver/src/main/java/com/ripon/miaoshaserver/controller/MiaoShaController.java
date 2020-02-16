package com.ripon.miaoshaserver.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.ripon.miaoshaserver.annotation.AccessLimit;
import com.ripon.miaoshaserver.annotation.VerifyToken;
import com.ripon.miaoshaserver.domain.MiaoShaUser;
import com.ripon.miaoshaserver.dto.GoodsDTO;
import com.ripon.miaoshaserver.rabbitmq.MQSender;
import com.ripon.miaoshaserver.rabbitmq.MiaoshaMessage;
import com.ripon.miaoshaserver.redis.RedisService;
import com.ripon.miaoshaserver.result.CodeMessageEnum;
import com.ripon.miaoshaserver.result.Result;
import com.ripon.miaoshaserver.service.GoodsService;
import com.ripon.miaoshaserver.service.MiaoShaService;
import com.ripon.miaoshaserver.service.OrderService;
import com.ripon.miaoshaserver.service.UserService;
import com.ripon.miaoshaserver.util.UuidUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MiaoShaController implements InitializingBean {
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoShaService miaoShaService;
    @Autowired
    RedisService redisService;
    @Autowired
    MQSender mqSender;
    @Autowired
    DefaultKaptcha kaptcha;

    private Map<Long, Boolean> overFlags = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsDTO> goodsList = goodsService.listMiaoshaGoods();
        for (GoodsDTO goods : goodsList) {
            Long goodsId = goods.getId();
            Integer stock = goods.getMiaoshaStock();
            redisService.setGoodsStock(goodsId.toString(), stock);
            overFlags.put(goodsId, false);
        }

    }

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @GetMapping("/miaoshaPath")
    public Result getMiaoshaPath(@RequestParam Long goodsId, @RequestParam String verifyCode, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        if (!checkVerifyCode(userId, goodsId, verifyCode)) {
            return Result.error(CodeMessageEnum.VERIFY_CODE_ERROR);
        }
        String path = DigestUtils.md5DigestAsHex(UuidUtils.uuid().getBytes());
        redisService.setSeckillPath(userId, goodsId, path);
        return Result.success(path);
    }

    @VerifyToken
    @GetMapping("/verifyCode")
    public Result getVerifyCode(@RequestParam Long goodsId, HttpServletRequest request) throws IOException{
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        String text = kaptcha.createText();
        String s = produceVerifyCode(text);
        redisService.setVerifyCode(userId, goodsId, text);
        return Result.success(s);
    }

    private String produceVerifyCode(String text) throws IOException{
        BufferedImage image = kaptcha.createImage(text);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg" , outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        ByteArrayOutputStream bs = new ByteArrayOutputStream();
//        ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
//        ImageIO.write(image, "jpg", imOut);
//        InputStream inputStream = new ByteArrayInputStream(bs.toByteArray());
//
//        OutputStream outStream = new FileOutputStream("C:\\Users\\29040\\Desktop\\yanzhengma.jpg");
//        IOUtils.copy(inputStream, outStream);
//        inputStream.close();
//        outStream.close();
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    private boolean checkVerifyCode (Long userId, Long goodsId, String verifyCode) {
        String s = redisService.getVerifyCode(userId, goodsId);
        return verifyCode.equals(s);
    }

    // 300/sec
    // 650/sec
    @VerifyToken
    @GetMapping("{path}/seckill")
    public Result seckill(@RequestParam Long goodsId, HttpServletRequest request, @PathVariable("path") String path) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        MiaoShaUser user = userService.getUserById(userId);
        String realPath = redisService.getSeckillPath(userId, goodsId);
        if (!path.equals(realPath)) {
            return Result.error(CodeMessageEnum.REQUEST_ILLEGAL);
        }
        // 内存标记，减少redis访问
        boolean isOver = overFlags.get(goodsId);
        if (isOver) {
            return Result.error(CodeMessageEnum.MIAOSHA_OVER);
        }
        // 判断是否重复秒杀
        boolean isRepeat = redisService.checkSeckillRecord(goodsId, userId);
        if (isRepeat) {
            return Result.error(CodeMessageEnum.REPEAT_MIAOSHA);
        }
        // 预减库存
        Long stock = redisService.reduceStock(goodsId.toString());
        // 判断是否还有库存
        if (stock < 0) {
            overFlags.put(goodsId, true);
            return Result.error(CodeMessageEnum.MIAOSHA_OVER);
        }
        MiaoshaMessage message = new MiaoshaMessage();
        message.setGoodsId(goodsId);
        message.setUser(user);
        mqSender.sendMessage(message);
        return Result.success(0);

//        // 判断是否重复秒杀
//        List<MiaoShaOrder> list = orderService.getByGoodsIdAndUserId(goodsId, userId);
//        if (list.size() != 0) {
//            return Result.error(CodeMessageEnum.REPEAT_MIAOSHA);
//        }

//        GoodsDTO goodsDTO = goodsService.getGoodsDTOById(goodsId);
//        Integer stock = goodsDTO.getMiaoshaStock();
//        // 判断是否还有库存
//        if (stock < 0) {
//            return Result.error(CodeMessageEnum.MIAOSHA_OVER);
//        }
//        // 判断是否重复秒杀
//        List<MiaoShaOrder> list = orderService.getByGoodsIdAndUserId(goodsId, userId);
//        if (list.size() != 0) {
//            return Result.error(CodeMessageEnum.REPEAT_MIAOSHA);
//        }
//        // 减库存，下订单，写入秒杀订单
//        Order order = miaoShaService.miaosha(user, goodsDTO);
//        return Result.success(order);

    }

    @VerifyToken
    @GetMapping("/miaoshaResult")
    public Result getMiaoshaResult(@RequestParam Long goodsId, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        Long result = miaoShaService.getMiaoshaResult(goodsId, userId);
        if ( result == -1) {
            return Result.error(CodeMessageEnum.MIAOSHA_OVER);
        } else if ( result == 0) {
            return Result.error(CodeMessageEnum.MIAOSHA_FAIL);
        }
        return Result.success(result);
    }

    // 570/sec
    @GetMapping("/miaoshaGoods")
    public Result listMiaoshaGoods() {
        List<GoodsDTO> list = goodsService.listMiaoshaGoods();
        return Result.success(list);
    }

    @GetMapping("/miaoshaGoods/{id}")
    public Result getMiaoshaGoods(@PathVariable("id") Long id) {
        GoodsDTO goods = goodsService.getGoodsDTOById(id);
        Long startTime = goods.getStartTime().getTime();
        Long endTime = goods.getEndTime().getTime();
        Long currentTime = System.currentTimeMillis();
        Long duration = (endTime - startTime) / 1000;
        Long remainSeconds = (startTime - currentTime) / 1000;
        Map<String, Object> map = new HashMap<>();
        map.put("goods", goods);
        map.put("remainSeconds", remainSeconds);
        map.put("duration", duration);
        return Result.success(map);
    }
}
