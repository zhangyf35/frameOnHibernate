package org.webframe.tools.systemUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * FusionCharts图表工具类
 * @decription 封装了FusionCharts生成XML数据的方法
 * @author Zebe
 * @date 2015/3/5
 * @version 1.0.0
 */
@SuppressWarnings("all")
public class FusionChartsUtil {
	
	private String caption; // 标题
	private String clickURL; // 点击链接
	private List<DateSet> dataSets = new ArrayList<DateSet>(); // 数据集合
	private Set<String> categoryList = new LinkedHashSet(); // 板块集合

	/**
	 * 测试方法入口
	 * @param args
	 */
	public static void main(String args[]) {
		test1();
		//test2();
	}

	/**
	 * 测试方法1
	 * @param args
	 */
	public static void test1() {
		FusionChartsUtil helper = new FusionChartsUtil();
		helper.setCaption("会员注册统计");
		helper.addCategory("1月");
		helper.addCategory("2月");
		helper.addCategory("3月");
		helper.addCategory("4月");
		FusionChartsUtil.DateSet dataSet1 = helper.createDataSet();
		FusionChartsUtil.DateSet dataSet2 = helper.createDataSet();
		dataSet1.setSeriesName("司机");
		dataSet1.addSetItem(500, "ff0000");
		dataSet1.addSetItem(600, "ff0000");
		dataSet1.addSetItem(700, "ff0000");
		dataSet1.addSetItem(800, "ff0000");
		dataSet2.setSeriesName("普通用户");
		dataSet2.addSetItem(200, "ff0000");
		dataSet2.addSetItem(300, "ff0000");
		dataSet2.addSetItem(400, "ff0000");
		dataSet2.addSetItem(600, "ff0000");
		helper.addDateSet(dataSet1);
		helper.addDateSet(dataSet2);
		System.out.println(helper.createChartXmlData());
	}

	/**
	 * 测试方法2
	 * @param args
	 */
	public void test2() {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		Map map1 = new HashMap();
		map1.put("category", "8月");
		map1.put("value", 1200);
		map1.put("series", "普通用户");

		Map map2 = new HashMap();
		map2.put("category", "8月");
		map2.put("value", 800);
		map2.put("series", "司机");

		Map map3 = new HashMap();
		map3.put("category", "9月");
		map3.put("value", 1100);
		map3.put("series", "普通用户");

		Map map4 = new HashMap();
		map4.put("category", "9月");
		map4.put("value", 500);
		map4.put("series", "司机");

		Map map5 = new HashMap();
		map5.put("category", "10月");
		map5.put("value", 120);
		map5.put("series", "司机");

		Map map6 = new HashMap();
		map6.put("category", "10月");
		map6.put("value", 900);
		map6.put("series", "普通用户");

		Map map7 = new HashMap();
		map7.put("category", "10月");
		map7.put("value", 120);
		map7.put("series", "企业主");

		mapList.add(map1);
		mapList.add(map2);
		mapList.add(map3);
		mapList.add(map4);
		mapList.add(map5);
		mapList.add(map6);
		mapList.add(map7);
		System.out.println(mapList);
		//System.out.println(createChartXmlDataByMapList("测试", mapList));
	}

	/**
	 * 添加一个数据集合
	 * @param dataSet
	 */
	public void addDateSet(DateSet dataSet) {
		dataSets.add(dataSet);
	}

	/**
	 * 添加一个板块
	 * @param category
	 */
	public void addCategory(String category) {
		categoryList.add(category);
	}

	/**
	 * 创建一个数据集合
	 * @return DateSet
	 */
	public DateSet createDataSet(){
		return new DateSet();
	}

	/**
	 * 生成表格XML数据
	 * @return String
	 */
	public String createChartXmlData() {
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version='1.0' encoding='GBK'?>");
		builder.append("<chart showValues='1' baseFontSize='14'  bgColor='FFFFFF'  caption='"+caption+"'  showPlotBorder='0' ");
		//        if (StringUtils.isNotBlank(clickURL)) {
		//            builder.append("clickURL='" + clickURL + "' ");
		//        }
		builder.append("shownames='1' showvalues='0'   showSum='1' decimals='0' overlapColumns='0' plotGradientColor=''>");
		StringBuilder categoriesBuilder = new StringBuilder("<categories>");
		for (String category : categoryList) {
			categoriesBuilder.append("<category label='" + category + "' />");
		}
		categoriesBuilder.append("</categories>");
		builder.append(categoriesBuilder);

		StringBuilder datasetBuilder = new StringBuilder();
		for (DateSet dataSet : dataSets) {
			datasetBuilder.append(dataSet.toString());
		}
		builder.append(datasetBuilder);
		builder.append("</chart>");

		return builder.toString();
	}

	/**
	 * 生成表格XML数据
	 * @return String
	 */
	public static String createChartXmlDataByMapList(String caption, List<Map<String, String>> mapList) {
		if(mapList.size() != 0 ){
			FusionChartsUtil helper = new FusionChartsUtil();
			helper.setCaption(caption);

			// 得到所有板块
			List<String> catgoryList = new ArrayList<String>();
			for (Map map : mapList) {
				String cat = map.get("category").toString();
				if (! catgoryList.contains(cat)) {
					catgoryList.add(cat);
				}
			}

			// 得到所有系列
			List<String> seriesList = new ArrayList<String>();
			for (Map map : mapList) {
				String ser = map.get("series").toString();
				if (! seriesList.contains(ser)) {
					seriesList.add(ser);
				}
			}

			// 遍历系列
			for (String series : seriesList) {
				FusionChartsUtil.DateSet dataSet = helper.createDataSet();
				dataSet.setSeriesName(series);
				// 遍历板块
				for (String category : catgoryList) {
					helper.addCategory(category);
					// 遍历Map集合
					String tempValue = "";
					for (Map map : mapList) {
						String cat = map.get("category").toString();
						String val = map.get("value").toString();
						String ser = map.get("series").toString();
						// 找到当前板块和系列对应的值
						if (category.equals(cat) && series.equals(ser)) {
							tempValue = val;
						}
					}
					if ("".equals(tempValue)) {
						dataSet.addSetItem(0, null);
					} else {
						if(String.valueOf(tempValue).contains(".")){
							double a=Double.valueOf(tempValue);
							dataSet.addSetItem(Double.valueOf(tempValue), null);
						}else{
							dataSet.addSetItem(Long.valueOf(tempValue), null);
						}
					}
				}
				helper.addDateSet(dataSet);
			}			
			return helper.createChartXmlData();
		}else{
			return "";
		}
		
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getClickURL() {
		return clickURL;
	}

	public void setClickURL(String clickURL) {
		this.clickURL = clickURL;
	}

	/**
	 * DataSet数据集合内部类
	 * @description 该类是内部类，封装了一个数据集的全部方法和属性
	 * @author Zebe
	 * @date 2015/3/5
	 * @version 1.0.1
	 */
	public class DateSet {
		private String seriesName; // 系列名称 
		private StringBuilder setString = new StringBuilder(); // 数据值字符串

		/** 添加一个set
		 * @param value
		 * @param color
		 */
		public void addSetItem(Number value, String color) {
			if (color != null) {
				setString.append("<set value='" + value + "' color='" + color + "' showValues='0'/>");
			} else {
				setString.append("<set value='" + value + "' showValues='0'/>");
			}
		}

		public String getSeriesName() {
			return seriesName;
		}

		public void setSeriesName(String seriesName) {
			this.seriesName = seriesName;
		}

		/**
		 * 重写toString方法
		 */
		@Override
		public String toString() {
			return "<dataset seriesName='" + seriesName + "'>" + setString + "</dataset>";
		}
	}
	

}

