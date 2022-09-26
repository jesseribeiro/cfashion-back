package br.com.crista.fashion.bean;

import br.com.crista.fashion.enumeration.EnumRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Audited
@Getter
@Setter
@Entity ( name = "Role")
public class RoleBean extends GenericBean {

    @Enumerated(EnumType.STRING)
    private EnumRole nome;
}
