package com.testonline.util;

import com.testonline.custom.calculation.TIKI;
import com.testonline.table.Users;


public class Test {

	public static void main(String[] args) {
		Users u = new Users();
		u.setUserID("MEMBER01");
		for (TIKI p: new TIKI().listOfTIKI(u)) {
			System.out.println(p.getName() + " : " + p.getTotal());
		}
	}
}
