package com.library.model.Form;

import com.library.common.BasePagination;
import lombok.Data;

@Data
public class queryBorrowLogForm extends BasePagination {

    private String keyWords;
    private Integer sortType;
    private Integer status;

}
