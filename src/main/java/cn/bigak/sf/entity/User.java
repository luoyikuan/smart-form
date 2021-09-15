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
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "user", catalog = "sf")
public class User implements java.io.Serializable {


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "email", nullable = false, length = 32)
	private String email;
	
	@Column(name = "pwd", nullable = false, length = 16)
	private String pwd;
	
	@Column(name = "role", nullable = false)
	private Short role;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Form> forms = new HashSet<Form>(0);


	public User() {
	}

	public User(String email, String pwd, Short role) {
		this.email = email;
		this.pwd = pwd;
		this.role = role;
	}

	public User(String email, String pwd, Short role, Set<Form> forms) {
		this.email = email;
		this.pwd = pwd;
		this.role = role;
		this.forms = forms;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Short getRole() {
		return this.role;
	}

	public void setRole(Short role) {
		this.role = role;
	}


	public Set<Form> getForms() {
		return this.forms;
	}

	public void setForms(Set<Form> forms) {
		this.forms = forms;
	}

}