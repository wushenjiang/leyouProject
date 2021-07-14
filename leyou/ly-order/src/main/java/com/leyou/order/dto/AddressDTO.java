package com.leyou.order.dto;

import lombok.Data;

/**
 * @ClassName: AdressDTO
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/17  16:53
 */
@Data
public class AddressDTO {
    private Long id;
    private String name;
    private String phone;
    private String state;
    private String city;
    private String district;
    private String address;
    private String zipCode;
    private Boolean isDefault;
}
