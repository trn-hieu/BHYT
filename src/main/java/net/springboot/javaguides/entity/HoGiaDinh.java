package net.springboot.javaguides.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "hogiadinh")
public class HoGiaDinh {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(mappedBy = "hoGiaDinh")
	private List<CaNhan> caNhans;

	public HoGiaDinh() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HoGiaDinh(List<CaNhan> caNhans) {
		super();
		this.caNhans = caNhans;
	}
	
	public static Long Calculate(List<CaNhan> list) {
		int size=list.size();
		Long total=0L;
		if(size==1)
			total =804600L;
		else if(size==2) total =804600L + 563220 ;
		else if(size==3) total=804600L + 563220 + 482760 ;
		else if(size==4) total=804600L + 563220 + 482760 +402300 ;
		else if(size==5) total=804600L + 563220 + 482760 +402300 + 321840 ;
		else if(size>5) total=804600L + 563220 + 482760 +402300 + 321840+321840*(size-5);
		return total;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<CaNhan> getCaNhans() {
		return caNhans;
	}

	public void setCaNhans(List<CaNhan> caNhans) {
		this.caNhans = caNhans;
	}
	
	
}
