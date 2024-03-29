package swingEx4;


public class prediction {
	public static String[][] getTable1() {	//csv파일 상위 5 칼럼
	    String[][] arr = {
	        {"0",	"N"	,"N",	"2010",	"넥센",	"0.262",	"133",	"5139"	,"4452",	"570",	"1165",	"...",
	        		"9",	"160",	"68"	,"29.8",	"191", "135",	"56",	"70.7"	,"56",	"5"},
	        {"Y",	"2010",	"두산",	"0.281",	"133",	"5252"	,"4538"	,"731",	"1276",	"..."	,"8"	,"119"	,"44",
	        			"27",	"189",	"128",	"61",	"67.7",	"56"	,"12"},
	        {"Y",	"2010"	,"롯데",	"0.288",	"133",	"5261","4673"	,"773",	"1345",	"..."	,"6"	,"131",	"62",
	        				"32.1",	"175",	"124",	"51",	"70.9"	,"62"	,"7"},
	        {"Y",	"2010"	,"삼성",	"0.272",	"133",	"5287"	,"4436"	,"681"	,"1207",	"...",	"6"	,"134"	,"65",
	        					"32.7"	,"213",	"158"	,"55",	"74.2",	"54",	"8"},
	        {"N"	,"2010",	"한화",	"0.244"	,"133"	,"5009"	,"4321",	"543",	"1053",	"...",	"7",	"151",	"58",
	        						"27.8",	"191"	,"121"	,"70",	"63.4",	"56",	"11"}
	    };
	    return arr;
	}
	public static String[][] getTable2() {	//각 성적의 평균값을 'WIN', 'POST', 'ALL' 세 가지 카테고리로 나누어 비교
	    String[][] arr = {
	        { "7", 	 "Y", 	 "Y", 	 "2010", 	 "SK", 	 "0.274", 	 "133", 	 "5213", 	 "4439", 	 "704", 	 "1217",
	        		"...", 	 "7", 	 "109", 	 "59", 	 "35.1", 	 "230", 	 "161", 	 "69", 	 "70", 	 "51", 	 "13"},
	        { "11", 	 "Y", 	 "Y", 	 "2011", 	 "삼성", 	 "0.259", 	 "133", 	 "5145", 	 "4437", 	 "625", 	 "1150",
	        			"...", 	 "6", 	 "91", 	 "50", 	 "35.5", 	 "209", 	 "158", 	 "51", 	 "75.6", 	 "54", 	 "9"},
	        { "19", 	 "Y", 	 "Y", 	 "2012", 	 "삼성", 	 "0.272", 	 "133", 	 "5161", 	 "4436", 	 "628", 	 "1205", 
	        				"...", 	 "7", 	 "122", 	 "49", 	 "28.7", 	 "173", 	 "125", 	 "48", 	 "72.3", 	 "63", 	 "8"},
	        { "27", 	 "Y", 	 "Y", 	 "2013", 	 "삼성", 	 "0.283", 	 "128", 	 "5042", 	 "4370", 	 "669", 	 "1235", 
	        					"...", 	 "8", 	 "128", 	 "46", 	 "26.4", 	 "136", 	 "95", 	 "41", 	 "69.9", 	 "58", 	 "6"},
	        { "36", 	 "Y", 	 "Y", 	 "2014", 	 "삼성", 	 "0.301", 	 "128", 	 "5152", 	 "4464", 	 "812", 	 "1345", 	
	        			"...", 	 "8", 	 "100", 	 "44", 	 "30.6", 	 "207", 	 "161", 	 "46", 	 "77.8", 	 "60", 	 "10"},
	        { "45", 	 "Y", 	 "Y", 	 "2015", 	 "삼성", 	 "0.302", 	 "144", 	 "5803", 	 "5019", 	 "897", 	 "1515", 
	        				"...", 	 "8", 	 "80", 	 "49", 	 "38", 	 "209", 	 "157", 	 "52", 	 "75.1", 	 "60", 	 "6"},
	        { "53", 	 "Y", 	 "Y", 	 "2016", 	 "두산", 	 "0.298", 	 "144", 	 "5841", 	 "5044", 	 "935", 	 "1504", 	
	        					"...", 	 "4", 	 "82", 	 "44", 	 "34.9", 	 "130", 	 "85", 	 "45", 	 "65.4", 	 "64", 	 "4"},
	        { "67", 	 "Y", 	 "Y", 	 "2017", 	 "KIA", 	 "0.302", 	 "144", 	 "5841", 	 "5142", 	 "906", 	 "1554", 
	        					"...", 	 "12", 	 "69", 	 "45", 	 "39.5", 	 "110", 	 "76", 	 "34", 	 "69.1", 	 "46", 	 "8"},
	        { "73", 	 "Y", 	 "Y", 	 "2018", 	 "두산", 	 "0.309", 	 "144", 	 "5870", 	 "5176", 	 "944", 	 "1601", 
	        						"...", 	 "8", 	 "68", 	 "43", 	 "38.7", 	 "119", 	 "96", 	 "23", 	 "80.7", 	 "54", 	 "7"},
	        { "84", 	 "Y", 	 "Y", 	 "2019", 	 "두산", 	 "0.278", 	 "144", 	 "5670", 	 "4913", 	 "736", 	 "1364", 
	        							"...", 	 "6", 	 "96", 	 "32", 	 "25", 	 "140", 	 "102", 	 "38", 	 "72.9", 	 "59", 	 "7"},
	        { "92", 	 "Y", 	 "Y", 	 "2020", 	 "두산", 	 "0.293", 	 "144", 	 "5776", 	 "5046", 	 "816", 	 "1477", 	
	        							"...", 	 "6", 	 "91", 	 "25", 	 "21.6", 	 "125", 	 "88", 	 "37", 	 "70.4", 	 "63", 	 "4"},
	        { "105", 	 "Y", 	 "Y", 	 "2021", 	 "KT", 	 "0.265", 	 "144", 	 "5627", 	 "4810", 	 "719", 	 "1276", 	
	        							"...", 	 "7", 	 "93", 	 "31", 	 "25", 	 "158", 	 "112", 	 "46", 	 "70.9", 	 "50", 	 "6"}
	    };
	    return arr;
	}
	public static String[][] getTable3() {	//'AVG', 'OPS', 'RISP', 'PH-BA', 'ERA', 'WHIP', 'FPCT' 열만 포함하는 데이터프레임 상위 5개 행
	    String[][] arr = {
	        {"0",	"0.262",	"0.714",	"0.268",	"0.221",	"4.55",	"1.5",	"0.982"},
	        {"1",	"0.281",	"0.805",	"0.284",	"0.21",	"4.62",	"1.38",	"0.982"},
	        {"2",	"0.288",	"0.813",	"0.315",	"0.202",	"4.82",	"1.43",	"0.981"},
	        {"3",	"0.272",	"0.774",	"0.265",	"0.247",	"3.94",	"1.36",	"0.982"},
	        {"4",	"0.244",	"0.69",	"0.263",	"0.172",	"5.43",	"1.57",	"0.984"}
	    };
	    return arr;
	}
	public static String[][] getTable4() { //22시즌 순 예측
	    String[][] arr = {
	        {"KIA ",		"2.419%","3","5"},
	        {"삼성 ",	"0.2939%","7","7"},
	        {"LG ",		"36.87%","1","2"},
	        {"롯데 ",	"0.0957%","8","8"},
	        {"NC ",		"0.3478%","6","6"},
	        {"두산 ",	"0.03627%","9","9"},
	        {"SSG ",		"2.315%","4","1"},
	        {"KT ",		"2.802%","2","4"},
	        {"키움 ",		"0.8399%","5","3"},
	        {"한화 ",		"0.001972%","10","10"}
	    };
	    return arr;
	}
	public static String[][] getTable5() { //23시즌 순 예측
	    String[][] arr = {
	        {"KIA ",		"0.00047%","9","6"},
	        {"삼성 ",	"0.002032%","7","8"},
	        {"LG ",		"35.54%","3","1"},
	        {"롯데 ",	"0.001232%","8","7"},
	        {"NC ",		"35.6%","2","4"},
	        {"두산 ",		"0.09059%","5","5"},
	        {"SSG ",		"96.91%","1","3"},
	        {"KT ",		"33.66%","4","2"},
	        {"키움 ",	"0.0003793%","10","10"},
	        {"한화 ",		"0.05099%","6","9"}
	    };
	    return arr;
	}
	public static String[][] getTable6() { //변수중요도
	    String[][] arr = {
	        {"0",	"AVG",	"0.177844"},
	        {"1",	"OPS",	"0.529001"},
	        {"2",	"RISP",	"0.210723"},
	        {"3",	"PH-BA",	"0.032352"},
	        {"4",	"ERA",	"-1.274986"},
	        {"5",	"WHIP",	"-0.323772"},
	        {"6",	"FPCT",	"0.015854"}
	    };
	    return arr;
	}
}

