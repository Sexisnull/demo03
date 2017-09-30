package com.gsww.uids.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuthService {
	// 授权接口
	public String authGetToken(
			//@RequestParam("syscode") String syscode, // 系统Code
			//@RequestParam("username") String username, // 登录用户名
			//@RequestParam("password") String password// 登录密码
	);

	// 法人：实名比对--实名认证
	public String corporCont(@PathVariable("verify_mode") int verify_mode, // 查询比对模式
			//@PathVariable("syscode") String syscode, // 分配给的系统代码
			//@PathVariable("token") String token, // 登陆完成之后返回的Token
			@RequestParam("zzjgdm") String zzjgdm, // 组织机构代码
			@RequestParam("tyshxydm") String tyshxydm, // 统一社会信用代码
			@RequestParam("gszch") String gszch, // 工商注册号
			@RequestParam("qymcqc") String qymcqc// 企业名称全称
	);

	// 法人：实名认证--实名认证
	public String corporAuth(
			//@PathVariable("syscode") String syscode, // 分配给的系统代码
			//@PathVariable("token") String token, // 登陆完成之后返回的Token
			@PathVariable("verify_mode") int verify_mode, // 查询比对模式
			@RequestParam("frmc") String frmc, // 法人名称
			@RequestParam("dbrxm") String dbrxm, // 法定代表人姓名
			@RequestParam("id") String id, // 身份证号码
			@RequestParam("tyshxydm") String tyshxydm, // 统一社会信用代码
			@RequestParam("zzjgdm") String zzjgdm, // 组织机构代码
			@RequestParam("gszch") String gszch // 工商注册号
	);

	// 个人：验证个人信息
	public String perporAuth(
			//final @PathVariable("syscode") String syscode, // 分配给的系统代码
			//final @PathVariable("token") String req_token, // token
			final @RequestParam("id") String id,// 身份证号码
			final @RequestParam("name") String name// 姓名
	);

	// 公务人员：公务人员身份认证接口
	public String pubporAuth(
			final @PathVariable("verify_mode") int verify_mode, // 认证模式
			//final @PathVariable("syscode") String syscode, // 分配给的系统代码
			//final @PathVariable("token") String req_token, // token
			final @RequestParam("id") String id, // 身份证号码
			final @RequestParam("name") String name, // 姓名
			final @RequestParam("jgbm") String jgbm, // 统一身份认证的机构代码
			final @RequestParam("jgmc") String jgmc, // 机构名称
			final @RequestParam("qybm") String qybm// 区域编码
	);
}
