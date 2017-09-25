package com.gsww.uids.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.patchca.background.BackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@RequestMapping("/verifyCode")
public class VerifyCodeController
{
  private ConfigurableCaptchaService a = null;

  private ColorFactory b = null;

  private BackgroundFactory c = null;

  private RandomWordFactory d = null;

  private CurvesRippleFilterFactory e = null;

  public VerifyCodeController() {
    this.a = new ConfigurableCaptchaService();
    this.b = new SingleColorFactory(new Color(25, 60, 170));
    this.c = new BackgroundFactory() {
      public void fillBackground(BufferedImage paramBufferedImage) {
        Graphics localGraphics = paramBufferedImage.getGraphics();

        localGraphics.setColor(new Color(252, 252, 252));
      }
    };
    this.d = new RandomWordFactory();

    this.d.setCharacters("234578ABCEFGHJKLMNPQRSTUVWXYZacdefhjkmnprstuvwxyz");
    this.e = new CurvesRippleFilterFactory(this.a.getColorFactory());
    this.e.setColorFactory(this.b);

    this.a.setWordFactory(this.d);
    this.a.setColorFactory(this.b);
    this.a.setBackgroundFactory(this.c);
  }

  @RequestMapping
  public String getVerifyCode(@RequestParam(value="code", defaultValue="rand") String paramString1, @RequestParam(value="width", defaultValue="60") Integer paramInteger1, @RequestParam(value="height", defaultValue="20") Integer paramInteger2, HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession, HttpServletResponse paramHttpServletResponse)
  {
    try
    {
      this.a.setWidth(paramInteger1.intValue() * 2);
      this.a.setHeight(paramInteger2.intValue() * 2);
      paramHttpServletResponse.setContentType("image/png");
      paramHttpServletResponse.setHeader("cache", "no-cache");
      this.d.setMaxLength(4);
      this.d.setMinLength(4);
      ServletOutputStream localServletOutputStream = paramHttpServletResponse
        .getOutputStream();
      this.a.setFilterFactory(this.e);
      String str = EncoderHelper.getChallangeAndWriteImage(this.a, "png", 
        localServletOutputStream);

      paramHttpSession.setAttribute("rand", str.toLowerCase());

      localServletOutputStream.flush();
      localServletOutputStream.close();
    } catch (Exception localException) {
    }
    return null;
  }
}