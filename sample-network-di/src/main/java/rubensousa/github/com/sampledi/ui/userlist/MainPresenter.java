/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rubensousa.github.com.sampledi.ui.userlist;


import android.os.Bundle;
import android.support.annotation.Nullable;


import java.util.ArrayList;

import icepick.State;
import rubensousa.github.com.sampledi.data.model.User;
import rubensousa.github.com.sampledi.ui.base.BasePresenter;

public class MainPresenter extends BasePresenter<Main.View, Main.Interactor>
        implements Main.Presenter{

    @State
    boolean loading;

    @State
    boolean refreshing;

    @Override
    public Main.Interactor createInteractor() {
        return new MainInteractor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // onViewStateRestored will be called after
            loading = true;
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (loading) load();
        if (refreshing) refresh();
    }

    @Override
    public void load() {
        loading = true;

        if (isViewAttached()) {
            getView().showRefreshing(true);
        }

        getInteractor().load(new Main.Interactor.OnLoadListener() {
            @Override
            public void onLoadSuccess(ArrayList<User> users) {
                loading = false;
                getView().showRefreshing(false);
                getView().setUsers(users);
            }

            @Override
            public void onLoadError() {
                loading = false;
                getView().showRefreshing(false);
            }
        });
    }

    @Override
    public void refresh() {
        refreshing = true;

        if (isViewAttached()) {
            getView().showRefreshing(true);
        }

        getInteractor().refresh(new Main.Interactor.OnLoadListener() {
            @Override
            public void onLoadSuccess(ArrayList<User> users) {
                refreshing = false;
                getView().showRefreshing(false);
                getView().setUsers(users);
            }

            @Override
            public void onLoadError() {
                refreshing = false;
                getView().showRefreshing(false);
            }
        });
    }
}