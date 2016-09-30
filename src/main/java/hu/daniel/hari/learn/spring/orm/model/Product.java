package hu.daniel.hari.learn.spring.orm.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

	@Id
	private Integer id;
	private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductPart> parts = new HashSet<>();

	public Product() {
	}

	public Product(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parts=" + getParts() +
                '}';
    }

    public void setName(String name) {
		this.name = name;
	}

    public Set<ProductPart> getParts() {
        return parts;
    }

    public void setParts(Set<ProductPart> parts) {
        this.parts = parts;
        for (ProductPart p : this.parts) {
            p.setProduct(this);
        }
    }

}