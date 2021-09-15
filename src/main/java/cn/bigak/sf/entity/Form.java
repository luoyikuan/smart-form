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
 * Form entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "form", catalog = "sf")

public class Form implements java.io.Serializable {

	// Fields

	private Integer formId;
	private User user;
	private String formName;
	private Integer size;
	private Set<Input> inputs = new HashSet<Input>(0);

	// Constructors

	/** default constructor */
	public Form() {
	}

	/** minimal constructor */
	public Form(User user, String formName, Integer size) {
		this.user = user;
		this.formName = formName;
		this.size = size;
	}

	/** full constructor */
	public Form(User user, String formName, Integer size, Set<Input> inputs) {
		this.user = user;
		this.formName = formName;
		this.size = size;
		this.inputs = inputs;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "formId", unique = true, nullable = false)

	public Integer getFormId() {
		return this.formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "formName", nullable = false)

	public String getFormName() {
		return this.formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	@Column(name = "size", nullable = false)

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "form")

	public Set<Input> getInputs() {
		return this.inputs;
	}

	public void setInputs(Set<Input> inputs) {
		this.inputs = inputs;
	}

}