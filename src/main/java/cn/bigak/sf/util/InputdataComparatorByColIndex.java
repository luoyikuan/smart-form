package cn.bigak.sf.util;

import java.util.Comparator;

import cn.bigak.sf.entity.Inputdata;

public class InputdataComparatorByColIndex implements Comparator<Inputdata> {

	@Override
	public int compare(Inputdata inputdata1, Inputdata inputdata2) {
		return inputdata1.getInput().getColIndex().intValue() - inputdata2.getInput().getColIndex().intValue();
	}

}