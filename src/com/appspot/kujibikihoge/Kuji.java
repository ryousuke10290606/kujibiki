package com.appspot.kujibikihoge;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Kuji {

	//主キーフィールドにするために、@PrimaryKeyアノテーションを付与
	@PrimaryKey private String bangou;

	//データストアに保存するフィールドに、@Persistentアノテーションを付与
	@Persistent private String owner;

	//コンストラクタ
	public Kuji(String bangou,String owner) {
		this.bangou = bangou;
		this.owner =owner;
	}

	public String getBangou() {
		return bangou;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
