import java.util.Scanner;

public class OrderingMgr {

	public static void main(String[] args) {
//供选择的菜品信息
		String[] dishNames = { "重亲小面", "肉丝小面", "煎蛋小面", "牛肉小面" };
		double[] prices = { 8.0, 10.0, 11.0, 14.0 };
		int[] praiseNums = new int[4];
//数据主体：一组订单信息
		String[] names = new String[4]; // 1.数组保存订餐人姓名
		String[] dishMegs = new String[4]; // 2.数组保存选择菜品信息
		int[] times = new int[4]; // 3.数组保存送餐时间
		String[] addresses = new String[4]; // 4.数组保存送餐地址
		int[] states = new int[4]; // 5.数组保存订单状态（state）:0表示已预订，1表示已完成
		double[] sumPrices = new double[4]; // 6.数组保存订单的总金额
//初始化第一条订单信息
		names[0] = "张三";
		dishMegs[0] = "重亲小面 2份";
		times[0] = 12;
		addresses[0] = "科技大道—软件园2号楼509室";
		sumPrices[0] = 16.0;
		states[0] = 1;
//初始化第二条订单信息
		names[1] = "李四";
		dishMegs[1] = "肉丝小面 2份";
		times[1] = 18;
		addresses[1] = "科技大道—软件园2号楼509室";
		sumPrices[1] = 20.0;
		states[1] = 0;
//键盘输入
		Scanner sr = new Scanner(System.in);
		int num;
		boolean flag = false;
//循环：
		do {
//显示页
			System.out.println("欢迎使用“吃货联盟订单系统”\n**************************************");
			System.out
					.println("1、我要订餐\n2、查看餐袋\n3、签收订单\n4、删除订单\n5、我要点赞\n6、退出系统\n**************************************");
			System.out.print("请选择：");
			num = sr.nextInt();
			switch (num) {
			case 1:
				System.out.println("***我要订餐***");
				boolean add = true; // 记录是否可以订餐
				for (int j = 0; j < names.length; j++) {
					if (names[j] == null) { // 找到第一个空位置
						add = true; // 置标志位，可以订餐
						System.out.print("请输入订餐人姓名：");
						String name = sr.next();
//显示供选择的菜品信息
						System.out.println("序号\t餐名\t\t单价\t点赞数");
						for (int i = 0; i < dishNames.length; i++) {
							String price = prices[i] + "元";
							String praiseNum = (praiseNums[i]) > 0 ? praiseNums[i] + "赞" : "0";
							System.out.println((i + 1) + "\t" + dishNames[i] + "\t\t" + price + "\t" + praiseNum);
						}
						System.out.print("请选择您要点的菜品编号：");
						int chooseDish = sr.nextInt();
						System.out.print("请选择您需要的份数：");
						int number = sr.nextInt();
						String dishMeg = dishNames[chooseDish - 1] + " " + number + "份";
						double sumPrice = prices[chooseDish - 1] * number;
						// 餐费满50元，免送餐费
						double sendMoney = (sumPrice >= 50) ? 0 : 5;
						System.out.print("请输入送餐时间（送餐时间10:00~22:00）：");
						int time = sr.nextInt();
						while (time < 10 || time > 22) {
							System.out.println("您的输入有误，请输入10~22间的整数！");
							time = sr.nextInt();
						}
						System.out.print("请输入送餐地址：");
						String address = sr.next();
						// 无需添加状态，默认为0，即已预订状态
						System.out.println("订餐成功！");
						System.out.println("您订的是：" + dishMeg);
						System.out.println("送餐时间：" + time + "点");
						System.out.println("送餐地址：" + address);
						System.out.println(
								"餐费：" + sumPrice + "元，送餐费" + sendMoney + "元，总计：" + (sumPrice + sendMoney) + "元");
						// 添加数据

						names[j] = name;
						dishMegs[j] = dishMeg;
						times[j] = time;
						addresses[j] = address;
						sumPrices[j] = sumPrice + sendMoney;
						break;
					}
					if (!add) {
						System.out.println("对不起您的餐袋已满！");
					}
				}
				break;
			case 2:
				System.out.println("***查看餐袋***");
				System.out.println("序号\t订餐人\t餐品信息\t\t送餐时间\t送餐地址\t\t\t总金额\t订单状态");
				for (int i = 0; i < names.length; i++) {
					if (names[i] != null) {
						String state = (states[i] == 0) ? "已预订" : "已完成";// 判断订单状态
						String date = times[i] + "点";
						String sumPrice = sumPrices[i] + "元";
						System.out.println((i + 1) + "\t" + names[i] + "\t" + dishMegs[i] + "\t" + date + "\t"
								+ addresses[i] + "\t" + sumPrice + "\t" + state);
					}
				}
				break;
			case 3:
				System.out.println("***签收订单***");
				boolean signIn = false; // 标记是否找到要删除的订单
				System.out.print("请选择要签收的订单序号：");
				int indexSign = sr.nextInt();
				for (int i = 0; i < names.length; i++) {
					// 状态为已预订，序号为用户输入的订单序号减1：可签收
					// 状态为已完成，序号为用户输入的订单序号减1：不可签收
					if (names[i] != null && states[i] == 0 && indexSign == i + 1) {
						states[i] = 1; // 将状态值置为已完成
						System.out.println("订单签收成功！");
						signIn = true; // 标记已找到此订单
					} else if (names[i] != null && states[i] == 1 && indexSign == i + 1) {
						System.out.println("您选择的订单已完成签收，不能再次签收！");
						signIn = true;
					}
				}
				if (!signIn) {
					System.out.println("您选择的订单不存在！");
				}
				break;
			case 4:
				System.out.println("***删除订单***");
				boolean delFind = false;
				System.out.print("请输入要删除的订单序号：");
				int indexDel = sr.nextInt();
				for (int i = 0; i < names.length; i++) {
					// 状态值为已预订，序号为用户输入的订单序号减1：不可删除
					// 状态为为已完成，序号为用户输入的订单序号减1：可删除
					if (names[i] != null && states[i] == 1 && indexDel == i + 1) {
						delFind = true; // 标记已找到此订单
//删除
						for (int j = indexDel - 1; j < names.length - 1; j++) {
							names[j] = names[j + 1];
							dishMegs[j] = dishMegs[j + 1];
							times[j] = times[j + 1];
							addresses[j] = addresses[j + 1];
							states[j] = states[j + 1];
							sumPrices[j] = sumPrices[j + 1];
						}
//最后一位置空
						int endIndex = names.length - 1;
						names[endIndex] = null;
						dishMegs[endIndex] = null;
						times[endIndex] = 0;
						addresses[endIndex] = null;
						states[endIndex] = 0;
						sumPrices[endIndex] = 0;
						System.out.println("删除订单成功");
						break;
					} else if (names[i] != null && states[i] == 0 && indexDel == i + 1) {
						System.out.println("您选择的订单未签收，不能删除！");
						delFind = true; // 标记已找到此订单
						break;
					}
				}
//未找到该序号的订单：不能删除
				if (!delFind) {
					System.out.println("您要删除的订单不存在！");
				}
				break;
			case 5:
				System.out.println("***我要点赞***");
//显示菜品信息
				System.out.println("序号\t餐名\t\t单价\t点赞数");
				for (int i = 0; i < dishNames.length; i++) {
					String price = prices[i] + "元";
					String praiseNum = (praiseNums[i]) > 0 ? praiseNums[i] + "赞" : "0";
					System.out.println((i + 1) + "\t" + dishNames[i] + "\t\t" + price + "\t" + praiseNum);
				}
				System.out.print("请选择您要点赞的菜品序号：");
				int praiseNum = sr.nextInt();
				praiseNums[praiseNum - 1]++; // 点赞数加1
				System.out.println("点赞成功！");
				break;
			case 6:
				flag = true;
				System.out.println("***谢谢惠顾，欢迎您再次使用！***");
				break;
			default:
				flag = true;
				System.out.println("***谢谢惠顾，欢迎您再次使用！***");
				break;
			}
			if (!flag) {
				System.out.print("输入0返回：");
				num = sr.nextInt();
			} else {
				break;
			}

		} while (num == 0);
		sr.close();
	}

}
