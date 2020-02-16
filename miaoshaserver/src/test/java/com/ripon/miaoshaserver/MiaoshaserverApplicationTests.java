package com.ripon.miaoshaserver;

import com.ripon.miaoshaserver.dao.MiaoShaGoodsMapper;
import com.ripon.miaoshaserver.dao.MiaoShaOrderMapper;
import com.ripon.miaoshaserver.domain.MiaoShaGoods;
import com.ripon.miaoshaserver.domain.MiaoShaOrder;
import com.ripon.miaoshaserver.domain.MiaoShaOrderExample;
import com.ripon.miaoshaserver.dto.GoodsDTO;
import com.ripon.miaoshaserver.service.GoodsService;
import com.ripon.miaoshaserver.service.OrderService;
import com.ripon.miaoshaserver.util.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoshaserverApplicationTests {
    @Autowired
    UserUtils userUtils;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;


    @Test
    public void contextLoads() {
        try{
            userUtils.createUser(10000);
        }catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(goodsService.listMiaoshaGoods());
//        goodsService.reduceMiaoshaStock(Long.parseLong("1"));
//        GoodsDTO goodsDTO = new GoodsDTO();
//        goodsDTO.setId(Long.parseLong("1"));
//        orderService.createOrder(Long.parseLong("13000000000"),goodsDTO);
    }

}
