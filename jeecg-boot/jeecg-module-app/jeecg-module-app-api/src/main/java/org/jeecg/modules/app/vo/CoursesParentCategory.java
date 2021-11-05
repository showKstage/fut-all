package org.jeecg.modules.app.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CoursesParentCategory {
    String parentCategoryName;
    List<String> subCategoty;
}
