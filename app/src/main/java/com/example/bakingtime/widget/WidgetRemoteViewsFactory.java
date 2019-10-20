package com.example.bakingtime.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingtime.R;
import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.utilities.AppConstants;

import java.util.List;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String SET_BACKGROUND_COLOR = "setBackgroundColor";
    private Context context;
    private List<Ingredient> ingredients;

    WidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;

        Bundle bundleExtra = intent.getBundleExtra(AppConstants.RECIPE_INGREDIENTS_BUNDLE);
        ingredients = bundleExtra.getParcelableArrayList(AppConstants.WIDGET_RECIPE_INGREDIENTS);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = ingredients.get(position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        remoteViews.setTextViewText(R.id.ingredient_name, ingredient.getName());
        remoteViews.setTextViewText(R.id.ingredient_quantity, ingredient.getQuantity());
        remoteViews.setInt(R.id.ingredient_widget_layout, SET_BACKGROUND_COLOR, position % 2 == 0 ? Color.WHITE : context.getResources().getColor(R.color.ingredientItem));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
