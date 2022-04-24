package com.getir.readingisgood.helper.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "customSequences")
public class CustomSequences {
    @Id
    private String id;
    private Long seq;
}
