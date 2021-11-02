package com.belpost.apas.persistance.entity.lookup;

import com.belpost.apas.persistance.entity.LookupEntity;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("office")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Office extends LookupEntity {

    @Id
    private Long id;

    @MappedCollection
    private Set <Office> subOffices = new HashSet<>();



    public void addSubOffice (Office subOffice) {
        this.subOffices.add(subOffice);
    }
}