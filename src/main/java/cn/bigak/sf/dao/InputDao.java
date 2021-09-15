package cn.bigak.sf.dao;

import java.util.List;

import cn.bigak.sf.entity.Input;

public interface InputDao {

	boolean saveInput(Input input);

	Input getInputById(int inputId);

	List<Input> getInputsByFormId(int formId);
}
