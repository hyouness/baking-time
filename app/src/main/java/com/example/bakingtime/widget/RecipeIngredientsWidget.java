package com.example.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.bakingtime.R;
import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.utilities.AppConstants;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider {

    private static String recipeName;
    static ArrayList<Ingredient> ingredients;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action != null && action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            recipeName = intent.getStringExtra(AppConstants.RECIPE_NAME);
            ingredients = intent.getParcelableArrayListExtra(AppConstants.WIDGET_RECIPE_INGREDIENTS);

            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, RecipeIngredientsWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widget_listView);
        }

        super.onReceive(context, intent);

    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = getRemoteViews(context, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getRemoteViews(Context context, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);
        views.setTextViewText(R.id.widget_title_textView, getWidgetText(context));
        views.setViewVisibility(R.id.widget_error_text, recipeName == null ? View.VISIBLE : View.INVISIBLE);
        views.setRemoteAdapter(R.id.widget_listView, getRemoteViewsServiceIntent(context, appWidgetId));
        return views;
    }

    private static CharSequence getWidgetText(Context context) {
        return recipeName != null ? recipeName : context.getString(R.string.app_name);
    }

    private static Intent getRemoteViewsServiceIntent(Context context, int appWidgetId) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstants.WIDGET_RECIPE_INGREDIENTS, ingredients);

        Intent intent = new Intent(context, WidgetRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        // This operation has the functional value of changing the intent object
        // every time to avoid caching and allow for ingredients update.
        // Credit: https://stackoverflow.com/a/13203924/5826864
        intent.putExtra(recipeName, recipeName);

        intent.putExtra(AppConstants.RECIPE_INGREDIENTS_BUNDLE, bundle);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        return intent;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

