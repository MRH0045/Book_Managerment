package com.library.model.Form;

import com.library.common.BasePagination;
import lombok.Data;

@Data
public class queryBooksForm extends BasePagination {

    private String namePub;
    private String keyWords;
    private Integer bookKind;
    private Integer bookSite;
    private Integer sortType;

}
