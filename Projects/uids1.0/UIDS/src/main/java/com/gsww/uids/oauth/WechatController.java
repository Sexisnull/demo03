package com.gsww.uids.oauth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * WechatController
 * com.gsww.uids.oauth
 *
 * @author xiaoyy
 * wechatController
 * @Date 2017-08-23 下午4:33
 * The word 'impossible' is not in my dictionary.
 */
@RestController
@RequestMapping("/wechat")
public class WechatController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "adsb";
    }
}
