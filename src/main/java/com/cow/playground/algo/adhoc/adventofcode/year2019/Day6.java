package com.cow.playground.algo.adhoc.adventofcode.year2019;

import java.util.*;

/**
 * https://adventofcode.com/2019/day/6
 * 
 * @author quynh
 *
 */
public class Day6 {

	private static final int UNVISITED = -1;

	public static void main(String[] args) {
		part1();
		part2();
	}

	private static void part1() {
		String[] orbits = { "RN2)HSX", "TYQ)JFL", "PBD)P1X", "69L)R17", "LWG)VPQ", "TL1)MC6", "LV4)LFV", "LGY)LF3",
				"Y8D)CMZ", "N5F)CR9", "C6M)J97", "M39)BZP", "MNY)8NQ", "NVF)HXQ", "FKJ)KP3", "5YR)87D", "CYL)677",
				"JPT)ZL6", "79B)J1Q", "JYH)TS6", "LJN)QY3", "61T)YKQ", "3CM)8DT", "6FM)D8L", "TJ5)G4G", "1KL)BGT",
				"371)95C", "7VQ)241", "GWX)KNY", "7NS)VYG", "N5J)9K1", "VH9)Q3H", "4SL)JRQ", "6VB)Z96", "Z68)YY6",
				"RHD)S1K", "FP5)XJH", "BWC)98G", "JKT)PTH", "YKL)THM", "TJJ)BXL", "VM9)LV1", "W8C)P7R", "C5J)8SL",
				"7DG)RN2", "JQD)KXN", "C53)DX6", "RST)4DP", "652)FG6", "1PB)CCQ", "KY5)HBS", "J68)GZT", "GMJ)WLX",
				"ZM1)DFF", "38F)S9G", "869)TYQ", "534)H7C", "BXH)YQV", "FNP)15P", "YKK)F27", "925)HTT", "1FD)J77",
				"8RN)N35", "88J)65N", "9DM)CW4", "DSH)DXM", "QSK)2WM", "6DH)C53", "544)K2Z", "F65)9QT", "1H2)8MY",
				"6CS)F61", "SD1)82Q", "SW2)C5J", "96X)3CF", "M8G)4VR", "QML)XM8", "5C1)8R1", "2R9)YNZ", "PY5)LFR",
				"TL1)ZKK", "HHD)794", "TKX)LNB", "95C)BVL", "9NM)8HJ", "YKQ)Z29", "YMG)388", "ZGV)XTB", "6PN)F7C",
				"DNQ)GSQ", "6T7)GZZ", "W3G)9X3", "F5T)KL4", "4H2)QBC", "HQC)GG4", "1N7)GH9", "JT2)QNR", "ZHD)CTW",
				"3SS)9S4", "Q3H)2TB", "KXN)NV4", "13K)9WV", "PP1)VWR", "1YJ)BGM", "6RC)M6W", "NYZ)5P6", "HTT)HP1",
				"712)PSR", "DP8)N9L", "B2R)8N1", "4VR)FP5", "T3X)FCR", "PG9)NFN", "25P)D1Q", "NFK)CV8", "KGJ)G5S",
				"QRL)C4N", "BDM)F95", "Y1T)5C1", "WLX)HX3", "12X)9TL", "BPD)SZJ", "GMP)RHF", "W4D)YYS", "722)JVQ",
				"JJ4)3FG", "3FG)X4Q", "ZM6)3G3", "MC6)L7Q", "DPB)M39", "MQF)W28", "87V)8ZV", "7DG)M3Y", "1VN)MFS",
				"9Q6)3C9", "7MP)17N", "Y5M)Y3X", "6N3)LTV", "VQ3)W8G", "QRK)GD2", "LNB)LFL", "DKF)RL2", "1T5)W95",
				"VZ5)K5V", "76Q)W4D", "QH2)8JR", "2WM)S9C", "9X1)V35", "KZN)YRP", "9X5)T47", "FPS)TCH", "YRJ)Y6B",
				"67X)SFG", "D68)YQ6", "YKV)B2L", "6PN)7FT", "KWW)ZQ6", "ZTS)2BS", "HG6)2B8", "9K1)62W", "4YH)J98",
				"RRG)FJW", "J98)QK3", "LRZ)P76", "83Y)GXQ", "838)X49", "4LB)GC3", "2R4)DPB", "MC4)31H", "M8Z)FKJ",
				"8R1)NDM", "GNX)GYF", "MFL)DL7", "WGD)JSX", "SGN)TKX", "C2D)P6P", "LHH)G31", "PN6)959", "Q4Q)1CD",
				"3GL)82F", "MS5)SPX", "7GZ)7MC", "JRQ)RWY", "51T)C2D", "9N6)PR9", "CTW)Y54", "MJW)LJ7", "QBC)PW7",
				"NDM)RD5", "K85)KWW", "HBS)T66", "PC2)H7X", "MFS)VF9", "4J5)X6P", "JP8)GF2", "7VZ)C3H", "6HK)KVS",
				"BKR)96X", "QMF)JT2", "XDC)FLD", "TG3)XDC", "CCQ)CH7", "48C)CTS", "9WV)1YK", "31G)M8Z", "RYN)HV3",
				"WC6)9DH", "H7C)XT5", "36P)SX4", "P28)RWX", "H7C)L59", "P3K)RKB", "HP3)MS5", "Y1F)X6X", "LZV)F4K",
				"6TR)33Y", "YQ6)N8D", "MMP)6NJ", "4R8)J47", "9JV)JF2", "15R)ZQP", "CH7)31G", "FJW)1KF", "YSV)G5C",
				"LKV)C6M", "W28)HP3", "33Y)MQF", "V35)PN6", "24J)6PN", "ZJ1)QH2", "BGT)S8W", "61W)MD5", "GZZ)M6B",
				"FCR)427", "RD5)WGD", "72B)9Q6", "62W)51T", "82Q)CQH", "MZT)TL1", "D8M)T2B", "R8X)8YD", "F1K)9WC",
				"ZL6)3LX", "M6K)KTL", "GY6)GYN", "V8Q)J88", "WHH)STN", "XCC)WQK", "LGC)5JG", "S9G)FDZ", "LR8)W6M",
				"FC5)HX2", "MBC)M6F", "JSX)P3K", "K5J)Y9T", "ZRC)99H", "RGR)HHD", "QK2)GYY", "Y93)8QK", "Z5Z)7VQ",
				"DM6)41X", "ZQP)KWZ", "T4S)8PB", "66B)SGZ", "2Z3)L28", "CR9)ZTW", "79G)MFL", "M56)X79", "JLJ)CJ9",
				"SD7)JZT", "KVS)YSV", "K2M)869", "BX1)371", "F4K)1H2", "RZQ)MP3", "HX2)GLM", "3M6)PY5", "YY6)M6G",
				"32F)MWS", "1JC)SPG", "Y54)GQT", "1BN)MBC", "7MC)9JV", "G35)RTC", "38H)J68", "CFB)ZLH", "YYS)81D",
				"J2H)557", "L57)N6J", "5YR)LLG", "SX4)JN2", "PMV)44K", "ZH2)XSN", "QTJ)V8J", "5DZ)VZ5", "VYL)165",
				"PR9)W41", "NLD)61V", "TKD)H4G", "45M)H25", "9PF)WT5", "7FT)YM1", "LBT)32F", "SJS)224", "7NW)874",
				"DXZ)SW2", "XR2)SLK", "TD9)PC2", "1X1)WLM", "57Y)C1Y", "HCF)3GL", "7W2)2BB", "5RT)6T7", "YLZ)DGZ",
				"MBX)7LN", "F9G)MJW", "88P)8WS", "DRM)HCZ", "B7Z)BBH", "VD9)LKV", "MJW)J9N", "JYK)5CF", "5FJ)6TR",
				"DG4)QML", "MTY)55J", "NRM)BX1", "CRP)1KL", "YHP)JNJ", "DPH)N4X", "ZLH)FNK", "QZ7)JTK", "W9Y)4P2",
				"ZCQ)HCF", "Y9K)MBX", "ZGV)F9G", "Z96)W9Y", "XQT)YTZ", "J88)FPS", "8DT)FWK", "T88)2L9", "FCH)ZCZ",
				"HWZ)7PJ", "VW3)P5F", "X8G)BW9", "GZD)VQ3", "SPX)LCT", "QK3)7MP", "31H)1M3", "MR9)RGR", "JF6)3PF",
				"7GK)JM7", "Y41)QK2", "54M)1JC", "12D)B2R", "X77)ZHD", "3D6)5J6", "X79)ZRC", "3HJ)K1M", "W9Y)8PW",
				"H59)YLZ", "5YF)Z3M", "44V)ZGV", "GH9)LPP", "QTS)BHF", "WM1)8RN", "LHN)QSG", "YSQ)Y9R", "1YK)RYN",
				"JQK)QJ9", "JYH)VD9", "H84)4KZ", "8GC)7W2", "63N)QTS", "9CK)N89", "241)RZQ", "DBQ)D4H", "MTY)12X",
				"TWS)YRD", "83H)5YF", "LHN)5CM", "683)BPV", "SLK)WTG", "1N7)9HJ", "RKB)TGL", "2W5)2FR", "K5V)ZTD",
				"MZ7)5KW", "N8Y)DBX", "BBH)WLH", "7C3)F44", "M39)69L", "LV1)R1N", "QSK)PY6", "ZWN)CX1", "427)MTY",
				"DW7)1RY", "ZSQ)R5W", "6VT)22N", "557)2Y7", "QGN)P7Z", "F65)NYZ", "RJ9)QGN", "ZCZ)KJ3", "Z1S)PKJ",
				"Y4F)TWS", "QH7)ZH2", "J8X)P5R", "2BB)GF4", "726)YP6", "YK5)7GZ", "LTV)7JD", "2NN)1ZW", "BVS)9CK",
				"361)FRD", "T21)712", "LVY)W8C", "V39)Z1S", "2S6)HQC", "RWY)MKM", "L28)F97", "PNB)B7G", "HCZ)HWZ",
				"MHT)RST", "34X)9SM", "KJ3)79F", "K8L)8GC", "FRD)9T6", "KK5)G2K", "82F)KYL", "5LB)V8Q", "21L)46C",
				"MFR)VKC", "HMH)7T2", "XK9)CBP", "ZK3)X8C", "FVD)YKV", "JTK)CYN", "QDJ)3SS", "YNZ)DHZ", "F7R)7NW",
				"WLH)2T4", "1H2)D68", "98G)NLD", "959)PMV", "KMN)61T", "GQT)ZTS", "LF3)WHH", "3C9)J2X", "4K2)BJT",
				"R2K)5SJ", "W6M)N8P", "BJT)P4X", "41X)FS6", "MK8)KDB", "PJ7)HM1", "T5G)MHY", "8DT)FCH", "F9Y)BPT",
				"W8Q)3DD", "YQC)BGH", "NWN)C17", "MD5)Y1F", "MHY)QH7", "ZMH)NN8", "V32)WTX", "FK1)ZJ1", "SD7)1TM",
				"1TK)T4P", "79F)3S8", "C1Y)QTL", "ZML)PKM", "M3Y)2V5", "VQ8)ZMH", "S9C)M6K", "TKD)544", "DL7)K8L",
				"BHF)8BC", "WT5)NYF", "JD3)GNM", "GC3)32M", "5CF)XV8", "B3S)P5J", "5L7)5DV", "PGN)3JR", "2V5)843",
				"8NQ)RHD", "W5N)76Q", "GF4)QHF", "78B)MHT", "JZT)GZD", "RWX)FLN", "FGG)HN9", "MP3)B94", "F1C)79B",
				"C3H)7WK", "WLH)T21", "R5W)7VL", "6DS)3S6", "QNR)271", "Y75)95S", "K8V)T4J", "2DN)RCV", "3G3)34X",
				"84Z)V32", "2T4)TWG", "S26)814", "DPF)D8M", "N9D)THZ", "8S7)2CC", "8CX)CLG", "4DZ)RBH", "42G)DWS",
				"SFN)LZV", "P7Z)LTH", "G2K)J2H", "2L4)5YZ", "6S6)GDR", "2CC)MZ7", "WNX)T9K", "DHZ)LVY", "WQK)5WJ",
				"LS1)44V", "2XL)2GH", "X3D)VTB", "5KW)PP1", "4BH)V39", "STV)ZSQ", "83D)PH7", "VF9)VH9", "S8W)6VB",
				"F4B)Y75", "VYG)SFY", "KTL)45M", "LFL)PS5", "MWS)JLY", "SH3)39L", "9DH)61W", "MD4)NK9", "49C)2W5",
				"7CL)3TL", "CBP)2R4", "271)K7T", "3TL)5L7", "ZM1)ZML", "W9T)GBS", "6FL)LHN", "Z7W)1PB", "JNJ)XCC",
				"YTZ)6DS", "BST)4BW", "SZJ)DKF", "PTH)2S6", "2VH)361", "8BC)Q4B", "PSR)7WV", "4BB)32V", "PQL)TJ5",
				"D3X)TVD", "HYC)J8X", "ZPF)XXY", "1RT)BST", "Q3H)Q7C", "YVJ)ZWN", "XPX)1TK", "SJS)YK5", "22N)N72",
				"JRN)H84", "1T5)MD4", "XZG)4ZJ", "55J)8C7", "N8P)GMJ", "PSR)1VN", "YQV)FGH", "KRT)SZQ", "CP5)1N7",
				"Q5F)29K", "Q5P)QMF", "YP5)726", "YW9)4SL", "R8P)7ZB", "1RY)JF6", "ZQZ)R8P", "7W2)YHP", "2B8)SH3",
				"794)H59", "BBJ)27J", "VJF)S4R", "XPT)RV1", "7T2)ZXL", "GLM)3BF", "32M)SS5", "9HJ)JG2", "8MY)YKL",
				"QMF)JQJ", "PK5)CLP", "GYF)WY9", "G5C)JGL", "JG2)K8V", "YQC)79G", "4PL)L45", "32P)BY1", "ZDQ)925",
				"W41)G5P", "SM2)P87", "NBZ)5FJ", "814)KZN", "NJK)PNB", "FMY)NRM", "4VZ)CGG", "652)YKK", "WVB)BY5",
				"C4N)4R8", "9BM)87V", "9QT)HKS", "N6J)6HK", "8PW)B4S", "LPP)7NS", "HCF)DP8", "6DK)MC4", "4JV)48C",
				"WYH)JTD", "4D2)1SQ", "C17)MK8", "X77)WSM", "XTB)PCT", "J47)9X5", "3Q3)88P", "97J)C92", "HP1)HG6",
				"4P2)DW7", "843)D3X", "THZ)D4Q", "F44)36P", "P5R)WDY", "BZP)PJ7", "RDF)48T", "R17)7WG", "FWK)Y4F",
				"39X)JQD", "SY2)BDM", "DJN)Y93", "KTP)RTM", "73W)WNX", "935)LBT", "N2L)5DZ", "L7L)5MS", "224)785",
				"FDZ)GNX", "F3H)5WR", "LPB)YSQ", "DBV)Y9K", "ZKP)97J", "FJW)ZM1", "MYD)MM5", "COM)HM9", "SGZ)Q4Q",
				"NK9)4H2", "VM4)5G3", "Q7C)FPX", "FS6)M56", "XBR)N8Y", "BDL)SML", "YRD)HKR", "F7C)PRR", "388)RY4",
				"HV3)XBR", "M9X)NWN", "F27)CYL", "WDY)SJS", "ZR4)5LY", "L7Q)YP5", "YRP)DY2", "HX3)1SD", "R9H)PBD",
				"X8D)W3B", "Y41)6FM", "Z9F)4TR", "MM5)66B", "SML)38H", "NFN)1KN", "4W2)ZLV", "BZP)9X1", "Q7C)5DY",
				"1TM)HKB", "VKX)LWG", "FCR)F7F", "BY1)5J7", "THS)LKP", "WQK)C19", "17N)Y1T", "CKH)L57", "78P)NV5",
				"GF2)FGG", "FGG)SD1", "874)KTP", "JRR)CFB", "4VC)T3X", "DGZ)WZC", "1CD)83D", "XXY)S26", "LKH)JD3",
				"K1M)KK5", "8YQ)6V7", "TKR)VQ8", "N7H)ZZP", "R1N)F1C", "TPW)R2K", "JGL)XNJ", "5WJ)72B", "62W)ZPF",
				"KNY)C7F", "SPG)3PC", "FNK)NBZ", "HKB)NFK", "CJ9)9WW", "KWZ)M8G", "P28)NVF", "6V7)MYD", "ZYH)JYD",
				"XQT)VFM", "99H)9FJ", "4ML)GY6", "CP5)WM1", "X6P)STV", "1SQ)KRT", "7JD)J2B", "3JZ)F7R", "65N)QRK",
				"MW7)TFR", "2L9)ZDQ", "D4H)F9Y", "PS5)WTV", "G35)6QC", "LTH)9MY", "DQJ)2VH", "SPB)6HB", "L9W)L7L",
				"WNL)83H", "34G)JLJ", "8SL)H75", "JVQ)CKH", "165)VW3", "X4Q)DBQ", "VWR)7YW", "T9K)JW4", "79G)WYH",
				"S5T)K6R", "78P)QRX", "GZT)447", "3S6)4YH", "PCT)57Y", "39X)W9T", "Y9R)5RT", "8C7)DNQ", "9WC)MNY",
				"785)SFN", "RCV)42G", "D1Q)Q5P", "C4Y)DG4", "72G)78B", "XHT)S5T", "HX3)WTB", "JVC)9NM", "KMZ)WVB",
				"HN9)5LB", "WTV)JP8", "8JP)Z5Z", "VTB)WHR", "BWP)YTK", "PY6)DXZ", "5G3)2NN", "ZXL)3HJ", "3ZD)VKX",
				"4ZJ)8RD", "GWX)1FD", "WDY)4ML", "P4X)3D6", "JQJ)34G", "KNH)Q81", "HPR)SG1", "RY4)73V", "7ZB)BYD",
				"BPT)ZK3", "CLG)G7W", "3CF)1D1", "B5F)21L", "Z4Y)FBV", "GM9)JRR", "D9M)C7J", "B4S)ZKP", "P5J)32P",
				"T9K)FK1", "HXQ)7LT", "MSD)88Z", "5P6)JRN", "M91)JQK", "W82)ZSH", "WLM)935", "DY2)J81", "9XJ)4JZ",
				"N72)VM4", "T2B)V7B", "M6B)2XL", "J77)N5J", "9W2)T2J", "JFL)Q11", "FNK)V15", "W8G)QRL", "T4J)F5T",
				"G5S)F65", "G31)LGC", "8YD)6CS", "838)TWL", "CW4)RDF", "46C)GM9", "HSX)MTZ", "447)RJ9", "N4X)25P",
				"9FJ)6S6", "W8S)ZVM", "NN8)N7H", "6T7)M9L", "CV8)2L4", "ZZP)PYZ", "HKR)YXC", "C28)6VT", "FBV)QPZ",
				"WY9)BXH", "JF2)GSK", "PYR)195", "GD2)KY5", "1ZW)BKR", "CZL)VX9", "C7F)VP1", "VFM)CYB", "MC6)TZP",
				"C75)9BM", "GSQ)LHH", "J2X)3K1", "7CL)JJ4", "RHF)MZT", "5DV)3CM", "5MR)Z68", "6HZ)N2L", "D39)HYC",
				"D4Q)DV6", "2TB)H3R", "673)J27", "J81)B5F", "1D1)JKT", "44K)Y8D", "8PB)1T5", "C92)QZ7", "LW8)4TW",
				"3Q4)SGN", "FK1)D9Q", "ZSH)PK5", "BXL)6HZ", "FQ5)T5G", "KDB)C75", "3HJ)LRZ", "VCJ)LV4", "G7K)4JV",
				"HMP)54M", "7PJ)3Q4", "GH2)5R2", "B1W)DPH", "P6P)1YJ", "B9S)3JZ", "J1Q)NVV", "CQH)BWC", "48T)FNP",
				"Z29)86W", "NNC)LGY", "H3R)DPF", "CLP)JKB", "LLG)8TM", "TQQ)7C3", "8N1)XR2", "D9Q)B3S", "GC3)84Z",
				"QPZ)83Y", "5DR)JPT", "2BS)M2H", "F1T)XQT", "KYL)SPB", "VP1)Q6F", "P2H)NJK", "CTS)7VZ", "J27)YD4",
				"NRM)Z4Y", "RTC)M91", "7LT)D39", "W95)T88", "M6W)691", "KP3)BBJ", "SZJ)HR2", "V8J)88J", "8VG)S95",
				"R3K)VWT", "ZVM)XZG", "9MY)6L2", "L59)WGP", "5WR)LKH", "M9L)D2X", "JSF)X8D", "RGR)K85", "H7X)VJF",
				"7KT)Y26", "ZK3)PG9", "61V)YQC", "NYF)8YQ", "ZLV)WT4", "9X1)L9W", "CQK)GH2", "C7J)SD7", "FG6)15R",
				"K6R)Z9F", "SS5)WT1", "9X3)LJN", "JVQ)2Z3", "4BW)JRC", "5LY)XPX", "LJ7)D9M", "LRZ)PYR", "JW4)KT5",
				"HM9)LS1", "SG1)XHT", "HK8)4G3", "L45)KNH", "1KF)DJN", "KXG)TXP", "FPX)2DX", "DNQ)4K2", "DX6)W46",
				"7GH)3ZD", "MK8)5MR", "RKB)7DG", "QHF)X3D", "QJ9)5DR", "F97)PQL", "X6X)2DN", "PRR)ZYH", "CJ9)HK8",
				"677)MMP", "FRD)R3K", "13K)TJJ", "3PF)3FQ", "FGH)XX5", "N8D)9W2", "4DP)R9H", "3JR)YVJ", "B5F)VJB",
				"YTH)PJ2", "78M)4VZ", "PJ2)C28", "5DY)R8X", "TPW)HPR", "TZP)YW9", "JRC)4J5", "5SJ)XK9", "Y6B)67X",
				"B7G)K5J", "BGH)MW7", "8JR)T2Z", "VX9)HQZ", "XM8)B9S", "MS5)HLP", "9S4)4BH", "LFR)K2M", "5JG)3SP",
				"J2B)Z7W", "X49)TG3", "7LN)JPC", "GXQ)GS4", "6L2)TNX", "3FG)BDL", "T47)X77", "RX9)1X1", "GYY)KXG",
				"QS4)M9X", "Y26)VM9", "RTM)9DM", "3HZ)12D", "KK2)ZM6", "68X)N9D", "B7G)3M6", "DB3)4LB", "2DX)RZ9",
				"C19)CQK", "NBB)3HZ", "8TM)F3H", "WT4)4PL", "CZL)JYH", "15P)KK2", "9MV)DRM", "TFR)JVC", "ZQ6)TVW",
				"Z7W)DQJ", "8MW)QSK", "XJH)X8G", "P5F)NBB", "YQN)YMG", "F7F)73W", "Y3X)652", "HM1)Y41", "ZTD)TKR",
				"4YK)13K", "95J)1FJ", "1FJ)5YR", "PW7)534", "CX1)MFR", "DWS)TPW", "LFV)N5F", "427)P28", "K2Z)CP5",
				"V7B)SY2", "N8P)NNC", "F44)39X", "3FQ)2R9", "YXC)DM6", "ZTW)9XJ", "7WG)2Q8", "QTL)M6H", "F1K)LRD",
				"3BF)THS", "T66)FMY", "27J)1RT", "1KN)BPD", "GNM)9PF", "MTZ)QWN", "QSG)MF2", "8T7)683", "Z5Z)8S7",
				"8SH)W4F", "XX5)BWP", "9DH)KGJ", "M9X)6N3", "TGL)MR9", "MQF)FQ5", "TS6)6FL", "CYN)6DH", "195)SAN",
				"STN)WZL", "RBH)M8S", "73V)WNL", "BPV)HMH", "8QK)DBV", "WY9)JTV", "G7W)9N6", "TXP)4BB", "PH7)WC6",
				"CGG)1BN", "JF6)NNR", "5MS)DB3", "9SM)965", "D8L)916", "691)7CL", "4G3)8MW", "6NJ)T4S", "7WK)RX9",
				"JLY)Y5M", "DBX)J1K", "GBS)F4B", "HXQ)CRP", "WTX)8VG", "WGP)YOU", "TVW)G7K", "M2H)GKG", "65N)722",
				"5J7)XPT", "THM)78M", "QY3)CZL", "9SM)ZR4", "P76)W8S", "BYD)QTJ", "JYD)LW8", "4G3)W3G", "5R2)QS4",
				"2GH)HZ9", "LCT)F1K", "WSM)YTH", "21L)LR8", "HR2)W5N", "916)7KT", "9TL)7GH", "N9L)W8Q", "VJB)TD9",
				"FLN)838", "1SD)LPB", "HKS)49C", "88Z)YRJ", "CYB)68X", "CH7)JYK", "XV8)4YK", "PXJ)SM2", "2Q8)8T7",
				"6QC)G9M", "BVL)6DK", "J1K)FC5", "W3B)4DZ", "4TW)YQN", "HLP)8JP", "M8S)S76", "YD4)Q5F", "48T)GWX",
				"PKM)4W2", "86W)FG2", "JTD)JSF", "VJB)B7Z", "ZM6)P2H", "G9M)TKD", "ZQP)8XQ", "TWG)PXJ", "8XQ)3Q3",
				"GG4)ZQZ", "W4F)FVD", "WT1)673", "5CM)4D2", "K7T)24J", "9HJ)8SH", "Q6F)VYL", "8RD)78P", "JG2)6RC",
				"Q6F)BKL", "T2Z)KMZ", "LR8)KMN", "GKG)38F", "XT5)8CX", "FG2)VCJ", "V15)9MV", "GBS)4VC", "M6G)TQQ",
				"J9N)QDJ", "MF2)BVS", "BX1)C4Y", "1M3)95J", "J97)7GK", "VWT)PGN", "S9G)72G", "87D)B1W", "PYZ)F1T",
				"ZCZ)W82", "WHR)CRV", "7GK)63N", "JN2)RRG", "QRX)HMP", "4VR)ZCQ", "V8Q)GMP", "SFG)RXQ", "39L)G35",
				"WZL)DSH", "TWL)MSD" };

		Map<String, Integer> objects = new HashMap<>();
		int objectCount = 0;

		for (String orbit : orbits) {
			int sepIndex = orbit.indexOf(')');
			String u = orbit.substring(0, sepIndex);
			String v = orbit.substring(sepIndex + 1);
			if (objects.get(u) == null) {
				objects.put(u, objectCount++);
			}
			if (objects.get(v) == null) {
				objects.put(v, objectCount++);
			}
		}

		List<Set<Integer>> allEdges = new ArrayList<>();
		int[] incomings = new int[objectCount];
		int[] enclosingOrbits = new int[objectCount];
		Arrays.fill(enclosingOrbits, UNVISITED);

		for (int i = 0; i < objectCount; i++) {
			allEdges.add(new HashSet<Integer>());
		}

		for (String orbit : orbits) {
			int sepIndex = orbit.indexOf(')');
			int u = objects.get(orbit.substring(0, sepIndex));
			int v = objects.get(orbit.substring(sepIndex + 1));
			allEdges.get(u).add(v);
			incomings[v]++;
		}

		for (int u = 0; u < objectCount; u++) {
			if (incomings[u] == 0) {
				dfs(u, allEdges, enclosingOrbits, 0);
			}
		}

		int sum = 0;
		for (int x : enclosingOrbits) {
			sum += x;
		}

		System.out.println(sum);
	}

	private static void part2() {
		String[] orbits = { "RN2)HSX", "TYQ)JFL", "PBD)P1X", "69L)R17", "LWG)VPQ", "TL1)MC6", "LV4)LFV", "LGY)LF3",
				"Y8D)CMZ", "N5F)CR9", "C6M)J97", "M39)BZP", "MNY)8NQ", "NVF)HXQ", "FKJ)KP3", "5YR)87D", "CYL)677",
				"JPT)ZL6", "79B)J1Q", "JYH)TS6", "LJN)QY3", "61T)YKQ", "3CM)8DT", "6FM)D8L", "TJ5)G4G", "1KL)BGT",
				"371)95C", "7VQ)241", "GWX)KNY", "7NS)VYG", "N5J)9K1", "VH9)Q3H", "4SL)JRQ", "6VB)Z96", "Z68)YY6",
				"RHD)S1K", "FP5)XJH", "BWC)98G", "JKT)PTH", "YKL)THM", "TJJ)BXL", "VM9)LV1", "W8C)P7R", "C5J)8SL",
				"7DG)RN2", "JQD)KXN", "C53)DX6", "RST)4DP", "652)FG6", "1PB)CCQ", "KY5)HBS", "J68)GZT", "GMJ)WLX",
				"ZM1)DFF", "38F)S9G", "869)TYQ", "534)H7C", "BXH)YQV", "FNP)15P", "YKK)F27", "925)HTT", "1FD)J77",
				"8RN)N35", "88J)65N", "9DM)CW4", "DSH)DXM", "QSK)2WM", "6DH)C53", "544)K2Z", "F65)9QT", "1H2)8MY",
				"6CS)F61", "SD1)82Q", "SW2)C5J", "96X)3CF", "M8G)4VR", "QML)XM8", "5C1)8R1", "2R9)YNZ", "PY5)LFR",
				"TL1)ZKK", "HHD)794", "TKX)LNB", "95C)BVL", "9NM)8HJ", "YKQ)Z29", "YMG)388", "ZGV)XTB", "6PN)F7C",
				"DNQ)GSQ", "6T7)GZZ", "W3G)9X3", "F5T)KL4", "4H2)QBC", "HQC)GG4", "1N7)GH9", "JT2)QNR", "ZHD)CTW",
				"3SS)9S4", "Q3H)2TB", "KXN)NV4", "13K)9WV", "PP1)VWR", "1YJ)BGM", "6RC)M6W", "NYZ)5P6", "HTT)HP1",
				"712)PSR", "DP8)N9L", "B2R)8N1", "4VR)FP5", "T3X)FCR", "PG9)NFN", "25P)D1Q", "NFK)CV8", "KGJ)G5S",
				"QRL)C4N", "BDM)F95", "Y1T)5C1", "WLX)HX3", "12X)9TL", "BPD)SZJ", "GMP)RHF", "W4D)YYS", "722)JVQ",
				"JJ4)3FG", "3FG)X4Q", "ZM6)3G3", "MC6)L7Q", "DPB)M39", "MQF)W28", "87V)8ZV", "7DG)M3Y", "1VN)MFS",
				"9Q6)3C9", "7MP)17N", "Y5M)Y3X", "6N3)LTV", "VQ3)W8G", "QRK)GD2", "LNB)LFL", "DKF)RL2", "1T5)W95",
				"VZ5)K5V", "76Q)W4D", "QH2)8JR", "2WM)S9C", "9X1)V35", "KZN)YRP", "9X5)T47", "FPS)TCH", "YRJ)Y6B",
				"67X)SFG", "D68)YQ6", "YKV)B2L", "6PN)7FT", "KWW)ZQ6", "ZTS)2BS", "HG6)2B8", "9K1)62W", "4YH)J98",
				"RRG)FJW", "J98)QK3", "LRZ)P76", "83Y)GXQ", "838)X49", "4LB)GC3", "2R4)DPB", "MC4)31H", "M8Z)FKJ",
				"8R1)NDM", "GNX)GYF", "MFL)DL7", "WGD)JSX", "SGN)TKX", "C2D)P6P", "LHH)G31", "PN6)959", "Q4Q)1CD",
				"3GL)82F", "MS5)SPX", "7GZ)7MC", "JRQ)RWY", "51T)C2D", "9N6)PR9", "CTW)Y54", "MJW)LJ7", "QBC)PW7",
				"NDM)RD5", "K85)KWW", "HBS)T66", "PC2)H7X", "MFS)VF9", "4J5)X6P", "JP8)GF2", "7VZ)C3H", "6HK)KVS",
				"BKR)96X", "QMF)JT2", "XDC)FLD", "TG3)XDC", "CCQ)CH7", "48C)CTS", "9WV)1YK", "31G)M8Z", "RYN)HV3",
				"WC6)9DH", "H7C)XT5", "36P)SX4", "P28)RWX", "H7C)L59", "P3K)RKB", "HP3)MS5", "Y1F)X6X", "LZV)F4K",
				"6TR)33Y", "YQ6)N8D", "MMP)6NJ", "4R8)J47", "9JV)JF2", "15R)ZQP", "CH7)31G", "FJW)1KF", "YSV)G5C",
				"LKV)C6M", "W28)HP3", "33Y)MQF", "V35)PN6", "24J)6PN", "ZJ1)QH2", "BGT)S8W", "61W)MD5", "GZZ)M6B",
				"FCR)427", "RD5)WGD", "72B)9Q6", "62W)51T", "82Q)CQH", "MZT)TL1", "D8M)T2B", "R8X)8YD", "F1K)9WC",
				"ZL6)3LX", "M6K)KTL", "GY6)GYN", "V8Q)J88", "WHH)STN", "XCC)WQK", "LGC)5JG", "S9G)FDZ", "LR8)W6M",
				"FC5)HX2", "MBC)M6F", "JSX)P3K", "K5J)Y9T", "ZRC)99H", "RGR)HHD", "QK2)GYY", "Y93)8QK", "Z5Z)7VQ",
				"DM6)41X", "ZQP)KWZ", "T4S)8PB", "66B)SGZ", "2Z3)L28", "CR9)ZTW", "79G)MFL", "M56)X79", "JLJ)CJ9",
				"SD7)JZT", "KVS)YSV", "K2M)869", "BX1)371", "F4K)1H2", "RZQ)MP3", "HX2)GLM", "3M6)PY5", "YY6)M6G",
				"32F)MWS", "1JC)SPG", "Y54)GQT", "1BN)MBC", "7MC)9JV", "G35)RTC", "38H)J68", "CFB)ZLH", "YYS)81D",
				"J2H)557", "L57)N6J", "5YR)LLG", "SX4)JN2", "PMV)44K", "ZH2)XSN", "QTJ)V8J", "5DZ)VZ5", "VYL)165",
				"PR9)W41", "NLD)61V", "TKD)H4G", "45M)H25", "9PF)WT5", "7FT)YM1", "LBT)32F", "SJS)224", "7NW)874",
				"DXZ)SW2", "XR2)SLK", "TD9)PC2", "1X1)WLM", "57Y)C1Y", "HCF)3GL", "7W2)2BB", "5RT)6T7", "YLZ)DGZ",
				"MBX)7LN", "F9G)MJW", "88P)8WS", "DRM)HCZ", "B7Z)BBH", "VD9)LKV", "MJW)J9N", "JYK)5CF", "5FJ)6TR",
				"DG4)QML", "MTY)55J", "NRM)BX1", "CRP)1KL", "YHP)JNJ", "DPH)N4X", "ZLH)FNK", "QZ7)JTK", "W9Y)4P2",
				"ZCQ)HCF", "Y9K)MBX", "ZGV)F9G", "Z96)W9Y", "XQT)YTZ", "J88)FPS", "8DT)FWK", "T88)2L9", "FCH)ZCZ",
				"HWZ)7PJ", "VW3)P5F", "X8G)BW9", "GZD)VQ3", "SPX)LCT", "QK3)7MP", "31H)1M3", "MR9)RGR", "JF6)3PF",
				"7GK)JM7", "Y41)QK2", "54M)1JC", "12D)B2R", "X77)ZHD", "3D6)5J6", "X79)ZRC", "3HJ)K1M", "W9Y)8PW",
				"H59)YLZ", "5YF)Z3M", "44V)ZGV", "GH9)LPP", "QTS)BHF", "WM1)8RN", "LHN)QSG", "YSQ)Y9R", "1YK)RYN",
				"JQK)QJ9", "JYH)VD9", "H84)4KZ", "8GC)7W2", "63N)QTS", "9CK)N89", "241)RZQ", "DBQ)D4H", "MTY)12X",
				"TWS)YRD", "83H)5YF", "LHN)5CM", "683)BPV", "SLK)WTG", "1N7)9HJ", "RKB)TGL", "2W5)2FR", "K5V)ZTD",
				"MZ7)5KW", "N8Y)DBX", "BBH)WLH", "7C3)F44", "M39)69L", "LV1)R1N", "QSK)PY6", "ZWN)CX1", "427)MTY",
				"DW7)1RY", "ZSQ)R5W", "6VT)22N", "557)2Y7", "QGN)P7Z", "F65)NYZ", "RJ9)QGN", "ZCZ)KJ3", "Z1S)PKJ",
				"Y4F)TWS", "QH7)ZH2", "J8X)P5R", "2BB)GF4", "726)YP6", "YK5)7GZ", "LTV)7JD", "2NN)1ZW", "BVS)9CK",
				"361)FRD", "T21)712", "LVY)W8C", "V39)Z1S", "2S6)HQC", "RWY)MKM", "L28)F97", "PNB)B7G", "HCZ)HWZ",
				"MHT)RST", "34X)9SM", "KJ3)79F", "K8L)8GC", "FRD)9T6", "KK5)G2K", "82F)KYL", "5LB)V8Q", "21L)46C",
				"MFR)VKC", "HMH)7T2", "XK9)CBP", "ZK3)X8C", "FVD)YKV", "JTK)CYN", "QDJ)3SS", "YNZ)DHZ", "F7R)7NW",
				"WLH)2T4", "1H2)D68", "98G)NLD", "959)PMV", "KMN)61T", "GQT)ZTS", "LF3)WHH", "3C9)J2X", "4K2)BJT",
				"R2K)5SJ", "W6M)N8P", "BJT)P4X", "41X)FS6", "MK8)KDB", "PJ7)HM1", "T5G)MHY", "8DT)FCH", "F9Y)BPT",
				"W8Q)3DD", "YQC)BGH", "NWN)C17", "MD5)Y1F", "MHY)QH7", "ZMH)NN8", "V32)WTX", "FK1)ZJ1", "SD7)1TM",
				"1TK)T4P", "79F)3S8", "C1Y)QTL", "ZML)PKM", "M3Y)2V5", "VQ8)ZMH", "S9C)M6K", "TKD)544", "DL7)K8L",
				"BHF)8BC", "WT5)NYF", "JD3)GNM", "GC3)32M", "5CF)XV8", "B3S)P5J", "5L7)5DV", "PGN)3JR", "2V5)843",
				"8NQ)RHD", "W5N)76Q", "GF4)QHF", "78B)MHT", "JZT)GZD", "RWX)FLN", "FGG)HN9", "MP3)B94", "F1C)79B",
				"C3H)7WK", "WLH)T21", "R5W)7VL", "6DS)3S6", "QNR)271", "Y75)95S", "K8V)T4J", "2DN)RCV", "3G3)34X",
				"84Z)V32", "2T4)TWG", "S26)814", "DPF)D8M", "N9D)THZ", "8S7)2CC", "8CX)CLG", "4DZ)RBH", "42G)DWS",
				"SFN)LZV", "P7Z)LTH", "G2K)J2H", "2L4)5YZ", "6S6)GDR", "2CC)MZ7", "WNX)T9K", "DHZ)LVY", "WQK)5WJ",
				"LS1)44V", "2XL)2GH", "X3D)VTB", "5KW)PP1", "4BH)V39", "STV)ZSQ", "83D)PH7", "VF9)VH9", "S8W)6VB",
				"F4B)Y75", "VYG)SFY", "KTL)45M", "LFL)PS5", "MWS)JLY", "SH3)39L", "9DH)61W", "MD4)NK9", "49C)2W5",
				"7CL)3TL", "CBP)2R4", "271)K7T", "3TL)5L7", "ZM1)ZML", "W9T)GBS", "6FL)LHN", "Z7W)1PB", "JNJ)XCC",
				"YTZ)6DS", "BST)4BW", "SZJ)DKF", "PTH)2S6", "2VH)361", "8BC)Q4B", "PSR)7WV", "4BB)32V", "PQL)TJ5",
				"D3X)TVD", "HYC)J8X", "ZPF)XXY", "1RT)BST", "Q3H)Q7C", "YVJ)ZWN", "XPX)1TK", "SJS)YK5", "22N)N72",
				"JRN)H84", "1T5)MD4", "XZG)4ZJ", "55J)8C7", "N8P)GMJ", "PSR)1VN", "YQV)FGH", "KRT)SZQ", "CP5)1N7",
				"Q5F)29K", "Q5P)QMF", "YP5)726", "YW9)4SL", "R8P)7ZB", "1RY)JF6", "ZQZ)R8P", "7W2)YHP", "2B8)SH3",
				"794)H59", "BBJ)27J", "VJF)S4R", "XPT)RV1", "7T2)ZXL", "GLM)3BF", "32M)SS5", "9HJ)JG2", "8MY)YKL",
				"QMF)JQJ", "PK5)CLP", "GYF)WY9", "G5C)JGL", "JG2)K8V", "YQC)79G", "4PL)L45", "32P)BY1", "ZDQ)925",
				"W41)G5P", "SM2)P87", "NBZ)5FJ", "814)KZN", "NJK)PNB", "FMY)NRM", "4VZ)CGG", "652)YKK", "WVB)BY5",
				"C4N)4R8", "9BM)87V", "9QT)HKS", "N6J)6HK", "8PW)B4S", "LPP)7NS", "HCF)DP8", "6DK)MC4", "4JV)48C",
				"WYH)JTD", "4D2)1SQ", "C17)MK8", "X77)WSM", "XTB)PCT", "J47)9X5", "3Q3)88P", "97J)C92", "HP1)HG6",
				"4P2)DW7", "843)D3X", "THZ)D4Q", "F44)36P", "P5R)WDY", "BZP)PJ7", "RDF)48T", "R17)7WG", "FWK)Y4F",
				"39X)JQD", "SY2)BDM", "DJN)Y93", "KTP)RTM", "73W)WNX", "935)LBT", "N2L)5DZ", "L7L)5MS", "224)785",
				"FDZ)GNX", "F3H)5WR", "LPB)YSQ", "DBV)Y9K", "ZKP)97J", "FJW)ZM1", "MYD)MM5", "COM)HM9", "SGZ)Q4Q",
				"NK9)4H2", "VM4)5G3", "Q7C)FPX", "FS6)M56", "XBR)N8Y", "BDL)SML", "YRD)HKR", "F7C)PRR", "388)RY4",
				"HV3)XBR", "M9X)NWN", "F27)CYL", "WDY)SJS", "ZR4)5LY", "L7Q)YP5", "YRP)DY2", "HX3)1SD", "R9H)PBD",
				"X8D)W3B", "Y41)6FM", "Z9F)4TR", "MM5)66B", "SML)38H", "NFN)1KN", "4W2)ZLV", "BZP)9X1", "Q7C)5DY",
				"1TM)HKB", "VKX)LWG", "FCR)F7F", "BY1)5J7", "THS)LKP", "WQK)C19", "17N)Y1T", "CKH)L57", "78P)NV5",
				"GF2)FGG", "FGG)SD1", "874)KTP", "JRR)CFB", "4VC)T3X", "DGZ)WZC", "1CD)83D", "XXY)S26", "LKH)JD3",
				"K1M)KK5", "8YQ)6V7", "TKR)VQ8", "N7H)ZZP", "R1N)F1C", "TPW)R2K", "JGL)XNJ", "5WJ)72B", "62W)ZPF",
				"KNY)C7F", "SPG)3PC", "FNK)NBZ", "HKB)NFK", "CJ9)9WW", "KWZ)M8G", "P28)NVF", "6V7)MYD", "ZYH)JYD",
				"XQT)VFM", "99H)9FJ", "4ML)GY6", "CP5)WM1", "X6P)STV", "1SQ)KRT", "7JD)J2B", "3JZ)F7R", "65N)QRK",
				"MW7)TFR", "2L9)ZDQ", "D4H)F9Y", "PS5)WTV", "G35)6QC", "LTH)9MY", "DQJ)2VH", "SPB)6HB", "L9W)L7L",
				"WNL)83H", "34G)JLJ", "8SL)H75", "JVQ)CKH", "165)VW3", "X4Q)DBQ", "VWR)7YW", "T9K)JW4", "79G)WYH",
				"S5T)K6R", "78P)QRX", "GZT)447", "3S6)4YH", "PCT)57Y", "39X)W9T", "Y9R)5RT", "8C7)DNQ", "9WC)MNY",
				"785)SFN", "RCV)42G", "D1Q)Q5P", "C4Y)DG4", "72G)78B", "XHT)S5T", "HX3)WTB", "JVC)9NM", "KMZ)WVB",
				"HN9)5LB", "WTV)JP8", "8JP)Z5Z", "VTB)WHR", "BWP)YTK", "PY6)DXZ", "5G3)2NN", "ZXL)3HJ", "3ZD)VKX",
				"4ZJ)8RD", "GWX)1FD", "WDY)4ML", "P4X)3D6", "JQJ)34G", "KNH)Q81", "HPR)SG1", "RY4)73V", "7ZB)BYD",
				"BPT)ZK3", "CLG)G7W", "3CF)1D1", "B5F)21L", "Z4Y)FBV", "GM9)JRR", "D9M)C7J", "B4S)ZKP", "P5J)32P",
				"T9K)FK1", "HXQ)7LT", "MSD)88Z", "5P6)JRN", "M91)JQK", "W82)ZSH", "WLM)935", "DY2)J81", "9XJ)4JZ",
				"N72)VM4", "T2B)V7B", "M6B)2XL", "J77)N5J", "9W2)T2J", "JFL)Q11", "FNK)V15", "W8G)QRL", "T4J)F5T",
				"G5S)F65", "G31)LGC", "8YD)6CS", "838)TWL", "CW4)RDF", "46C)GM9", "HSX)MTZ", "447)RJ9", "N4X)25P",
				"9FJ)6S6", "W8S)ZVM", "NN8)N7H", "6T7)M9L", "CV8)2L4", "ZZP)PYZ", "HKR)YXC", "C28)6VT", "FBV)QPZ",
				"WY9)BXH", "JF2)GSK", "PYR)195", "GD2)KY5", "1ZW)BKR", "CZL)VX9", "C7F)VP1", "VFM)CYB", "MC6)TZP",
				"C75)9BM", "GSQ)LHH", "J2X)3K1", "7CL)JJ4", "RHF)MZT", "5DV)3CM", "5MR)Z68", "6HZ)N2L", "D39)HYC",
				"D4Q)DV6", "2TB)H3R", "673)J27", "J81)B5F", "1D1)JKT", "44K)Y8D", "8PB)1T5", "C92)QZ7", "LW8)4TW",
				"3Q4)SGN", "FK1)D9Q", "ZSH)PK5", "BXL)6HZ", "FQ5)T5G", "KDB)C75", "3HJ)LRZ", "VCJ)LV4", "G7K)4JV",
				"HMP)54M", "7PJ)3Q4", "GH2)5R2", "B1W)DPH", "P6P)1YJ", "B9S)3JZ", "J1Q)NVV", "CQH)BWC", "48T)FNP",
				"Z29)86W", "NNC)LGY", "H3R)DPF", "CLP)JKB", "LLG)8TM", "TQQ)7C3", "8N1)XR2", "D9Q)B3S", "GC3)84Z",
				"QPZ)83Y", "5DR)JPT", "2BS)M2H", "F1T)XQT", "KYL)SPB", "VP1)Q6F", "P2H)NJK", "CTS)7VZ", "J27)YD4",
				"NRM)Z4Y", "RTC)M91", "7LT)D39", "W95)T88", "M6W)691", "KP3)BBJ", "SZJ)HR2", "V8J)88J", "8VG)S95",
				"R3K)VWT", "ZVM)XZG", "9MY)6L2", "L59)WGP", "5WR)LKH", "M9L)D2X", "JSF)X8D", "RGR)K85", "H7X)VJF",
				"7KT)Y26", "ZK3)PG9", "61V)YQC", "NYF)8YQ", "ZLV)WT4", "9X1)L9W", "CQK)GH2", "C7J)SD7", "FG6)15R",
				"K6R)Z9F", "SS5)WT1", "9X3)LJN", "JVQ)2Z3", "4BW)JRC", "5LY)XPX", "LJ7)D9M", "LRZ)PYR", "JW4)KT5",
				"HM9)LS1", "SG1)XHT", "HK8)4G3", "L45)KNH", "1KF)DJN", "KXG)TXP", "FPX)2DX", "DNQ)4K2", "DX6)W46",
				"7GH)3ZD", "MK8)5MR", "RKB)7DG", "QHF)X3D", "QJ9)5DR", "F97)PQL", "X6X)2DN", "PRR)ZYH", "CJ9)HK8",
				"677)MMP", "FRD)R3K", "13K)TJJ", "3PF)3FQ", "FGH)XX5", "N8D)9W2", "4DP)R9H", "3JR)YVJ", "B5F)VJB",
				"YTH)PJ2", "78M)4VZ", "PJ2)C28", "5DY)R8X", "TPW)HPR", "TZP)YW9", "JRC)4J5", "5SJ)XK9", "Y6B)67X",
				"B7G)K5J", "BGH)MW7", "8JR)T2Z", "VX9)HQZ", "XM8)B9S", "MS5)HLP", "9S4)4BH", "LFR)K2M", "5JG)3SP",
				"J2B)Z7W", "X49)TG3", "7LN)JPC", "GXQ)GS4", "6L2)TNX", "3FG)BDL", "T47)X77", "RX9)1X1", "GYY)KXG",
				"QS4)M9X", "Y26)VM9", "RTM)9DM", "3HZ)12D", "KK2)ZM6", "68X)N9D", "B7G)3M6", "DB3)4LB", "2DX)RZ9",
				"C19)CQK", "NBB)3HZ", "8TM)F3H", "WT4)4PL", "CZL)JYH", "15P)KK2", "9MV)DRM", "TFR)JVC", "ZQ6)TVW",
				"Z7W)DQJ", "8MW)QSK", "XJH)X8G", "P5F)NBB", "YQN)YMG", "F7F)73W", "Y3X)652", "HM1)Y41", "ZTD)TKR",
				"4YK)13K", "95J)1FJ", "1FJ)5YR", "PW7)534", "CX1)MFR", "DWS)TPW", "LFV)N5F", "427)P28", "K2Z)CP5",
				"V7B)SY2", "N8P)NNC", "F44)39X", "3FQ)2R9", "YXC)DM6", "ZTW)9XJ", "7WG)2Q8", "QTL)M6H", "F1K)LRD",
				"3BF)THS", "T66)FMY", "27J)1RT", "1KN)BPD", "GNM)9PF", "MTZ)QWN", "QSG)MF2", "8T7)683", "Z5Z)8S7",
				"8SH)W4F", "XX5)BWP", "9DH)KGJ", "M9X)6N3", "TGL)MR9", "MQF)FQ5", "TS6)6FL", "CYN)6DH", "195)SAN",
				"STN)WZL", "RBH)M8S", "73V)WNL", "BPV)HMH", "8QK)DBV", "WY9)JTV", "G7W)9N6", "TXP)4BB", "PH7)WC6",
				"CGG)1BN", "JF6)NNR", "5MS)DB3", "9SM)965", "D8L)916", "691)7CL", "4G3)8MW", "6NJ)T4S", "7WK)RX9",
				"JLY)Y5M", "DBX)J1K", "GBS)F4B", "HXQ)CRP", "WTX)8VG", "WGP)YOU", "TVW)G7K", "M2H)GKG", "65N)722",
				"5J7)XPT", "THM)78M", "QY3)CZL", "9SM)ZR4", "P76)W8S", "BYD)QTJ", "JYD)LW8", "4G3)W3G", "5R2)QS4",
				"2GH)HZ9", "LCT)F1K", "WSM)YTH", "21L)LR8", "HR2)W5N", "916)7KT", "9TL)7GH", "N9L)W8Q", "VJB)TD9",
				"FLN)838", "1SD)LPB", "HKS)49C", "88Z)YRJ", "CYB)68X", "CH7)JYK", "XV8)4YK", "PXJ)SM2", "2Q8)8T7",
				"6QC)G9M", "BVL)6DK", "J1K)FC5", "W3B)4DZ", "4TW)YQN", "HLP)8JP", "M8S)S76", "YD4)Q5F", "48T)GWX",
				"PKM)4W2", "86W)FG2", "JTD)JSF", "VJB)B7Z", "ZM6)P2H", "G9M)TKD", "ZQP)8XQ", "TWG)PXJ", "8XQ)3Q3",
				"GG4)ZQZ", "W4F)FVD", "WT1)673", "5CM)4D2", "K7T)24J", "9HJ)8SH", "Q6F)VYL", "8RD)78P", "JG2)6RC",
				"Q6F)BKL", "T2Z)KMZ", "LR8)KMN", "GKG)38F", "XT5)8CX", "FG2)VCJ", "V15)9MV", "GBS)4VC", "M6G)TQQ",
				"J9N)QDJ", "MF2)BVS", "BX1)C4Y", "1M3)95J", "J97)7GK", "VWT)PGN", "S9G)72G", "87D)B1W", "PYZ)F1T",
				"ZCZ)W82", "WHR)CRV", "7GK)63N", "JN2)RRG", "QRX)HMP", "4VR)ZCQ", "V8Q)GMP", "SFG)RXQ", "39L)G35",
				"WZL)DSH", "TWL)MSD" };

		Map<String, Integer> objects = new HashMap<>();
		int objectCount = 0;
		int youOrbiting = -1;
		int sanOrbiting = -1;

		for (String orbit : orbits) {
			int sepIndex = orbit.indexOf(')');
			String u = orbit.substring(0, sepIndex);
			String v = orbit.substring(sepIndex + 1);
			if (objects.get(u) == null) {
				objects.put(u, objectCount++);
			}
			if (objects.get(v) == null) {
				objects.put(v, objectCount++);
				if ("YOU".equals(v)) {
					youOrbiting = objects.get(u);
				} else if ("SAN".equals(v)) {
					sanOrbiting = objects.get(u);
				}
			}
		}

		List<Set<Integer>> allEdges = new ArrayList<>();
		int[] incomings = new int[objectCount];

		for (int i = 0; i < objectCount; i++) {
			allEdges.add(new HashSet<Integer>());
		}

		for (String orbit : orbits) {
			int sepIndex = orbit.indexOf(')');
			int u = objects.get(orbit.substring(0, sepIndex));
			int v = objects.get(orbit.substring(sepIndex + 1));
			allEdges.get(u).add(v);
			allEdges.get(v).add(u);
		}

		int[] visit = new int[objectCount];
		Arrays.fill(visit, UNVISITED);
		Deque<Integer> queue = new ArrayDeque<>();
		queue.add(youOrbiting);
		visit[youOrbiting] = 0;
		while (!queue.isEmpty()) {
			int u = queue.removeFirst();
			for (int v : allEdges.get(u)) {
				if (visit[v] == UNVISITED) {
					visit[v] = visit[u] + 1;
					queue.addLast(v);
					if (v == sanOrbiting) {
						System.out.println(visit[v]);
						return;
					}
				}
			}
		}
		System.out.println(-1);
	}

	private static void dfs(int u, List<Set<Integer>> allEdges, int[] enclosingOrbits, int step) {
		enclosingOrbits[u] = step;
		for (Integer v : allEdges.get(u)) {
			if (enclosingOrbits[v] == UNVISITED) {
				dfs(v, allEdges, enclosingOrbits, step + 1);
			}
		}
	}
}