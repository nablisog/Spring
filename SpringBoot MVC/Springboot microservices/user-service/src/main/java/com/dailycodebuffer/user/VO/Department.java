package com.dailycodebuffer.user.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private Long departmentId;
    private String depatmentName;
    private String depatmentAddress;
    private String departmentCode;
}
