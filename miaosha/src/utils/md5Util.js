import md5 from 'js-md5'

export default function saltMD5 (password) {
  const salt = 'cvbijk'
  const str = password + salt
  return md5(str)
}
