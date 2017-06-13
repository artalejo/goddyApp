package goodyapp.example.com.goodyapp.domain.usercases;

import java.util.List;

import goodyapp.example.com.goodyapp.domain.usercases.base.UserCase;
import goodyapp.example.com.goodyapp.presentation.models.Good;

public interface GetGoodsUserCase extends UserCase {

    interface Callback {
        void onGoodsRetrieved(List<Good> goods);
    }
}
