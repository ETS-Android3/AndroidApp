package net.qiujuer.italker.factory.model.db;

import com.raizlabs.android.dbflow.structure.BaseModel;

import net.qiujuer.italker.factory.utils.DiffUiDataCallback;

public abstract class BaseDbModel<Model> extends BaseModel
        implements DiffUiDataCallback.UiDataDiffer<Model> {
}
