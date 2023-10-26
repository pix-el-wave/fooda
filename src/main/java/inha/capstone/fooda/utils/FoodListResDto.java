package inha.capstone.fooda.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FoodListResDto {
    private Integer no;
    private String name;
    private Integer size;
    private Float energy;
    private Float protein;
    private Float province;
    private Float carbohydrate;
    private Float calcium;
    private Float salt;
}
