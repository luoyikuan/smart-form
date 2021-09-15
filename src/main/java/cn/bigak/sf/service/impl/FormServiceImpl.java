package cn.bigak.sf.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bigak.sf.dao.FormDao;
import cn.bigak.sf.dao.InputDao;
import cn.bigak.sf.dao.InputdataDao;
import cn.bigak.sf.entity.Form;
import cn.bigak.sf.entity.Input;
import cn.bigak.sf.entity.Inputdata;
import cn.bigak.sf.entity.User;
import cn.bigak.sf.service.FormService;
import cn.bigak.sf.util.InputdataComparatorByColIndex;

@Service
public class FormServiceImpl implements FormService {
	@Autowired
	private FormDao formDao;
	@Autowired
	private InputDao inputDao;
	@Autowired
	private InputdataDao inputdataDao;

	@Override
	public boolean saveForm(String formHtml, User user) {
		List<Map<String, String>> formModel = null;
		try {
			formModel = parseFormHtml(formHtml);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Form form = new Form();
		form.setUser(user);
		form.setSize(0);
		form.setFormName(formModel.get(0).get("formName"));

		if (!formDao.saveForm(form)) {
			return false;
		}

		Input ipInput = new Input(form, (short) 0, "sys", "IP地址");
		Input dateInput = new Input(form, (short) 1, "sys", "提交时间");
		if (inputDao.saveInput(ipInput) == false || inputDao.saveInput(dateInput) == false) {
			return false;
		}

		short colIndex = 2;
		Map<String, String> inputModel = null;
		for (int i = 1; i < formModel.size(); i++) {
			inputModel = formModel.get(i);
			Input input = new Input(form, colIndex++, inputModel.get("type"), inputModel.get("label"));
			input.setValue(inputModel.get("value"));
			if (inputDao.saveInput(input) == false) {
				return false;
			}
		}

		return true;
	}

	// 后端解析html。。。这应该很蠢
	private List<Map<String, String>> parseFormHtml(String formHtml) throws Exception {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Document doc = Jsoup.parse(formHtml);
		Elements fieldsets = doc.getElementsByTag("fieldset");
		Element fieldset = fieldsets.get(0);

		// 获取表单名称
		Element element_form_name = fieldset.getElementById("legend").getElementsByTag("input").get(0);
		String str_form_name = element_form_name.attr("value");

		HashMap<String, String> map_form_name = new HashMap<String, String>();
		map_form_name.put("formName", str_form_name);
		result.add(map_form_name);

		Elements elements_control_group = fieldset.getElementsByClass("control-group");
		for (Element element : elements_control_group) {
			HashMap<String, String> map_input = new HashMap<String, String>();

			String label = element.getElementsByTag("label").get(0).html();
			map_input.put("label", label);

			Element element_controls = element.getElementsByClass("controls").get(0);

			if (element_controls.getElementsByTag("textarea").size() == 1) {
				map_input.put("type", "textarea");
				map_input.put("value", element_controls.getElementsByTag("textarea").get(0).html());
			} else if (element_controls.getElementsByTag("select").size() == 1) {
				map_input.put("type", "select");

				Elements elements_option = element_controls.getElementsByTag("select").get(0)
						.getElementsByTag("option");
				String values = "";
				for (int i = 0; i < elements_option.size(); i++) {
					if (i < elements_option.size() - 1) {
						values += elements_option.get(i).html() + "|";
					} else {
						values += elements_option.get(i).html();
					}
				}
				map_input.put("value", values);
			} else if (element_controls.getElementsByTag("input").size() > 0) {
				Elements elements_inputs = element_controls.getElementsByTag("input");
				String type = elements_inputs.get(0).attr("type");
				if ("text".equals(type)) {
					map_input.put("type", "text");
					map_input.put("value", elements_inputs.get(0).attr("value"));
				} else if ("checkbox".equals(type)) {
					map_input.put("type", "checkbox");

					String values = "";
					for (int i = 0; i < elements_inputs.size(); i++) {
						if (i < elements_inputs.size() - 1) {
							values += elements_inputs.get(i).attr("value") + "|";
						} else {
							values += elements_inputs.get(i).attr("value");
						}
					}
					map_input.put("value", values);

				} else if ("radio".equals(type)) {
					map_input.put("type", "radio");
					String values = "";
					for (int i = 0; i < elements_inputs.size(); i++) {
						if (i < elements_inputs.size() - 1) {
							values += elements_inputs.get(i).attr("value") + "|";
						} else {
							values += elements_inputs.get(i).attr("value");
						}
					}
					map_input.put("value", values);
				}

			}

			result.add(map_input);
		}

		return result;
	}

	@Override
	public Map<String, Object> getSubmit(int formId) {
		Map<String, Object> formModel = new HashMap<String, Object>();
		Form form = formDao.getFormById(formId);
		formModel.put("form", form);

		List<Input> inputs = inputDao.getInputsByFormId(formId);
		Collections.sort(inputs, new Comparator<Input>() {
			@Override
			public int compare(Input input1, Input input2) {
				return input1.getColIndex() - input2.getColIndex();
			}
		});

		inputs.remove(0);
		inputs.remove(0);

		formModel.put("inputs", inputs);

		return formModel;
	}

	@Override
	public boolean saveFormData(HttpServletRequest request, int formId) {
		Form form = formDao.getFormById(formId);
		List<Inputdata> inputdatas = new ArrayList<Inputdata>();

		for (Input input : form.getInputs()) {
			Inputdata inputdata = new Inputdata(input, form.getSize());
			if ("sys".equals(input.getType())) {
				if (input.getColIndex().intValue() == 0) {
					inputdata.setValue(request.getRemoteAddr());
				} else if (input.getColIndex().intValue() == 1) {
					inputdata.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			}
			inputdatas.add(inputdata);
		}

		InputdataComparatorByColIndex inputdataComparator = new InputdataComparatorByColIndex();
		Collections.sort(inputdatas, inputdataComparator);

		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = enu.nextElement();

			if (paraName.indexOf("field") == 0) {
				int colIndex = Integer.parseInt(paraName.substring(5));
				String[] cell = request.getParameterValues(paraName);
				if (cell.length == 1) {
					inputdatas.get(colIndex).setValue(cell[0]);
				} else {
					String value = "";
					for (int i = 0; i < cell.length; i++) {
						if (i < cell.length - 1) {
							value += cell[i] + "|";
						} else {
							value += cell[i];
						}
					}
					inputdatas.get(colIndex).setValue(value);
				}
			}

		}

		for (Inputdata inputdata : inputdatas) {
			if (inputdataDao.saveInputdata(inputdata) == false) {
				return false;
			}
		}

		form.setSize(form.getSize() + 1);
		return formDao.updateForm(form);
	}

	@Override
	public boolean updateForm(Form form) {
		return formDao.updateForm(form);
	}

	@Override
	public List<Form> getFormsByUserId(int userId) {
		return formDao.getFormsByUserId(userId);
	}

	@Override
	public int delForm(int formId, User user) {
		Form dbForm = formDao.getFormById(formId);
		if (dbForm == null) {
			return -1;
		}
		if (dbForm.getUser().getId().intValue() != user.getId().intValue()) {
			return -1;
		}
		return formDao.delForm(formId) ? 1 : 0;
	}

	@Override
	public int countDataRows(int formId) {
		return inputdataDao.countRowInputdatasByFormId(formId);
	}

	@Override
	public Map<String, Object> getFormModel(int formId) {
		int countRows = countDataRows(formId);

		Map<String, Object> root = new HashMap<String, Object>();
		root.put("count", countRows);

		List<Map<String, Object>> title = getTitle(formId);
		root.put("title", title);

		List<Input> inputs = inputDao.getInputsByFormId(formId);
		Input input = inputs.get(0);
		Set<Inputdata> inputdatas = input.getInputdatas();
		Set<Integer> rowIndexs = new TreeSet<Integer>();
		for (Inputdata inputdata : inputdatas) {
			rowIndexs.add(inputdata.getRowIndex());
		}

		List<List<Map<String, Object>>> data = new ArrayList<List<Map<String, Object>>>();

		InputdataComparatorByColIndex inputdataComparator = new InputdataComparatorByColIndex();
		for (Integer index : rowIndexs) {
			List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
			List<Inputdata> oneRowInputdatas = inputdataDao.getOneRowInputdatas(formId, index);
			Collections.sort(oneRowInputdatas, inputdataComparator);
			for (Inputdata inputdata : oneRowInputdatas) {
				Map<String, Object> cell = new HashMap<String, Object>();
				cell.put("id", inputdata.getInputDataId());
				cell.put("index", inputdata.getRowIndex());
				cell.put("value", inputdata.getValue());
				row.add(cell);
			}
			data.add(row);
		}
		root.put("data", data);

		return root;
	}

	@Override
	public Map<String, Object> getFormModelByPage(int formId, int pageSize, int pageIndex, String like) {
		if (like == null || (like = like.trim()).equals("")) {
			return getFormModelByPage(formId, pageSize, pageIndex);
		}
		Map<String, Object> root = new HashMap<String, Object>();
		List<Map<String, Object>> title = getTitle(formId);
		root.put("title", title);

		List<Inputdata> tempInputdatas = inputdataDao.getInputdataByFormId(formId, like);
		Set<Integer> rowIndexs = new TreeSet<Integer>();
		for (Inputdata inputdata : tempInputdatas) {
			rowIndexs.add(inputdata.getRowIndex());
		}
		root.put("count", rowIndexs.size());
		List<List<Map<String, Object>>> data = new ArrayList<List<Map<String, Object>>>();

		InputdataComparatorByColIndex inputdataComparator = new InputdataComparatorByColIndex();
		for (Integer index : rowIndexs) {
			List<Map<String, Object>> row = new ArrayList<Map<String, Object>>();
			List<Inputdata> oneRowInputdatas = inputdataDao.getOneRowInputdatas(formId, index);
			Collections.sort(oneRowInputdatas, inputdataComparator);
			for (Inputdata inputdata : oneRowInputdatas) {
				Map<String, Object> cell = new HashMap<String, Object>();
				cell.put("id", inputdata.getInputDataId());
				cell.put("index", inputdata.getRowIndex());
				cell.put("value", inputdata.getValue());
				row.add(cell);
			}
			data.add(row);
		}
		root.put("data", data);

		return specificPage(root, pageSize, pageIndex);
	}

	@Override
	public Map<String, Object> getFormModelByPage(int formId, int pageSize, int pageIndex) {
		Map<String, Object> formModel = getFormModel(formId);
		return specificPage(formModel, pageSize, pageIndex);
	}

	private List<Map<String, Object>> getTitle(int formId) {
		List<Input> inputs = inputDao.getInputsByFormId(formId);
		Collections.sort(inputs, new Comparator<Input>() {
			@Override
			public int compare(Input input1, Input input2) {
				return input1.getColIndex() - input2.getColIndex();
			}
		});
		List<Map<String, Object>> title = new ArrayList<Map<String, Object>>();
		for (Input input : inputs) {
			HashMap<String, Object> tCell = new HashMap<String, Object>();
			tCell.put("index", input.getColIndex());
			tCell.put("name", input.getName());
			title.add(tCell);
		}
		return title;
	}

	private Map<String, Object> specificPage(Map<String, Object> formModel, int pageSize, int pageIndex) {
		int count = ((Integer) formModel.get("count")).intValue();
		int pageCount = 0;

		if (pageSize < 1) {
			pageSize = 15;
		}

		pageCount = (count / pageSize) + ((count % pageSize > 0) ? 1 : 0);

		if (pageIndex >= pageCount) {
			pageIndex = pageCount - 1;
		}

		if (pageIndex < 0) {
			pageIndex = 0;
		}

		List<List<Map<String, Object>>> data = (List<List<Map<String, Object>>>) formModel.get("data");

		data = data.subList(pageIndex * pageSize,
				(pageIndex * pageSize + pageSize) > count ? count : (pageIndex * pageSize + pageSize));
		formModel.put("data", data);
		formModel.put("pageCount", pageCount);
		formModel.put("pageIndex", pageIndex);

		return formModel;

	}

	@Override
	public Form getFormById(int formId) {
		return formDao.getFormById(formId);
	}

}
