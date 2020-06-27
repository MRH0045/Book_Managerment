package com.library.model.Form;

import com.library.common.BasePagination;
import lombok.Data;

@Data
public class queryUserForm extends BasePagination {

    private String keyWords;
    private Integer sex;
    private Integer sortType;

}
