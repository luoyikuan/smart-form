package cn.bigak.sf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bigak.sf.dao.InputdataDao;
import cn.bigak.sf.entity.Inputdata;
import cn.bigak.sf.service.InputdataService;

@Service
public class InputdataServiceImpl implements InputdataService {
	@Autowired
	private InputdataDao inputdataDao;

	@Override
	public boolean delOneRow(int formId, int rowIndex) {
		return inputdataDao.delOneRowInputdata(formId, rowIndex);
	}

	@Override
	public boolean updateInputdataValueById(int id, String value) {
		return inputdataDao.updateValue(id, value);
	}

	@Override
	public Inputdata getInputdataById(int inputdataId) {
		return inputdataDao.getInputdataById(inputdataId);
	}

}
