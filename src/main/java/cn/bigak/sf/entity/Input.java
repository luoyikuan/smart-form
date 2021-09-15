package cn.bigak.sf.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Input entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "input", catalog = "sf")

public class Input implements java.io.Serializable {

	// Fields

	private Integer inputId;
	private Form form;
	private Short colIndex;
	private String type;
	private String name;
	private String value;
	private Set<Inputdata> inputdatas = new HashSet<Inputdata>(0);

	// Constructors

	/** default constructor */
	public Input() {
	}

	/** minimal constructor */
	public Input(Form form, Short colIndex, String type, String name) {
		this.form = form;
		this.colIndex = colIndex;
		this.type = type;
		this.name = name;
	}

	/** full constructor */
	public Input(Form form, Short colIndex, String type, String name, String value, Set<Inputdata> inputdatas) {
		this.form = form;
		this.colIndex = colIndex;
		this.type = type;
		this.name = name;
		this.value = value;
		this.inputdatas = inputdatas;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "inputId", unique = true, nullable = false)

	public Integer getInputId() {
		return this.inputId;
	}

	public void setInputId(Integer inputId) {
		this.inputId = inputId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "formId", nullable = false)

	public Form getForm() {
		return this.form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	@Column(name = "colIndex", nullable = false)

	public Short getColIndex() {
		return this.colIndex;
	}

	public void setColIndex(Short colIndex) {
		this.colIndex = colIndex;
	}

	@Column(name = "type", nullable = false, length = 16)

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "name", nullable = false)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "value")

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "input")

	public Set<Inputdata> getInputdatas() {
		return this.inputdatas;
	}

	public void setInputdatas(Set<Inputdata> inputdatas) {
		this.inputdatas = inputdatas;
	}

}