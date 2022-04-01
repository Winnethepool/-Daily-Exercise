package com.woniuxy.ui;

import java.util.Scanner;

import com.woniuxy.dao.BankDao;

/**
 * 界面类
 * @author Dell-G7
 *
 */
public class UI {
	Scanner input = new Scanner(System.in);
	BankDao bankDao = new BankDao();
	String currentAC;
	/**
	 * 一级菜单
	 */
	public void oneMenu() {
		System.out.println("-----------欢迎光临蜗牛ATM--------------");
		while(true) {
			System.out.println("------------请选择服务  1.登陆 2.注册 3.退出------------");
			String choose = input.next();
			if(choose.contentEquals("1")) {
				System.out.println("--------------登陆-------------");
				System.out.println("请输入您的帐号");
				String account = input.next();
				System.out.println("请输入您的密码");
				String password = input.next();
				currentAC = bankDao.logIn(account, password);
				if(currentAC != null) {
					System.out.println("登陆成功");
					twoMenu();
				}else {
					System.out.println("登陆失败");
				}
				
				
				
				
				
				
				
				
				
			}else if(choose.contentEquals("2")) {
				System.out.println("--------------注册-------------");
				System.out.println("请输入账号");
				String account = input.next();
				System.out.println("请输入密码");
				String password = input.next();
				String newS = bankDao.register(account, password);
				System.out.println(newS);
			}else if(choose.contentEquals("3")) {
				System.out.println("--------------See ya!------------");
				System.exit(0);
			}else {
				System.out.println("输入有误");
			}
		}
	}
	/**
	 * 二级菜单
	 */
	public void twoMenu() {	
		while(true) {
		System.out.println("----------请选择服务 1.查询余额  2.存款 3.取款 4.转账 5.返回上一级账单-------------");
		String choose = input.next();
		if(choose.equals("1")) {
			System.out.println("-------------查询余额-----------");	
			double qmoney = bankDao.moneyQuery(currentAC);
			System.out.println("您的余额为"+qmoney);
		}else if (choose.equals("2")) {
			System.out.println("-------------存款-----------");
			System.out.println("请输入您要存的金额");
			double smoney = input.nextDouble();
			bankDao.moneySave(currentAC, smoney);
		}else if (choose.equals("3")) {
			System.out.println("-------------取款------------");
			System.out.println("请输入您要取出的金额");
			double tmoney = input.nextDouble();
			int result = bankDao.moneyTakeoff(currentAC, tmoney);
			if(result != 0) {
				System.out.println("提款成功");
			}else {
				System.out.println("提款失败");
			}
		}else if (choose.equals("4")) {
			System.out.println("-------------转账------------");
			System.out.println("请输入转账的对象");
			String Inac = input.next();
			System.out.println("请输入金额");
			double money = input.nextDouble();
			bankDao.moneyTrans(Inac, currentAC, money);
		}else if (choose.equals("5")) {
			System.out.println("-------------返回上一级菜单------------");
			return;
		}else {
			System.out.println("-------------输入错误请重新输入----------");
		}
		}
	}
}
