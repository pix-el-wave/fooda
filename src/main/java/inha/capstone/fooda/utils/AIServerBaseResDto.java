package inha.capstone.fooda.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AIServerBaseResDto<T> {
    private T data;
    private Boolean success;
}