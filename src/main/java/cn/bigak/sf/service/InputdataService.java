package cn.bigak.sf.service;

import cn.bigak.sf.entity.Inputdata;

public interface InputdataService {

	boolean delOneRow(int formId, int rowIndex);

	boolean updateInputdataValueById(int id, String value);

	Inputdata getInputdataById(int inputdataId);
}
