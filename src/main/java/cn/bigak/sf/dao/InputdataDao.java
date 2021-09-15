package cn.bigak.sf.dao;

import java.util.List;

import cn.bigak.sf.entity.Inputdata;

public interface InputdataDao {

	boolean saveInputdata(Inputdata inputdata);

	boolean delOneRowInputdata(int formId, int rowIndex);

	Inputdata getInputdataById(int inputdataId);

	List<Inputdata> getOneRowInputdatas(int formId, int rowIndex);
	
	List<Inputdata> getInputdataByFormId(int formId);

	int countRowInputdatasByFormId(int formId);

	boolean updateValue(int inputdataId, String value);
	
	List<Inputdata> getInputdataByFormId(int formId, String like);
	
}
