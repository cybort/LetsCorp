package me.shouheng.commons.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import me.shouheng.commons.R;
import me.shouheng.commons.databinding.WidgetEmptyViewBinding;
import me.shouheng.commons.util.ColorUtils;
import me.shouheng.commons.util.PalmUtils;

/**
 * Created by wangshouheng on 2017/8/9. */
public class EmptyView extends LinearLayout {

    private boolean tintDrawable;

    private WidgetEmptyViewBinding binding;

    @DrawableRes
    private int mIcon;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.widget_empty_view, this, true);

        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.EmptyView, 0, 0);
        int bottomTitleSize = attr.getDimensionPixelSize(R.styleable.EmptyView_title_size, 16);
        int bottomSubTitleSize = attr.getDimensionPixelSize(R.styleable.EmptyView_sub_title_size, 14);
        mIcon = attr.getResourceId(R.styleable.EmptyView_empty_image, -1);
        tintDrawable = attr.getBoolean(R.styleable.EmptyView_tint_drawable, false);
        String bottomTitle = attr.getString(R.styleable.EmptyView_title);
        String bottomSubTitle = attr.getString(R.styleable.EmptyView_sub_title);
        attr.recycle();

        binding.tvBottomTitle.setText(bottomTitle);
        binding.tvBottomSubTitle.setText(bottomSubTitle);

        binding.tvBottomTitle.setTextSize(bottomTitleSize);
        binding.tvBottomSubTitle.setTextSize(bottomSubTitleSize);

        binding.tvBottomTitle.setVisibility(TextUtils.isEmpty(bottomTitle) ? GONE : VISIBLE);
        binding.tvBottomSubTitle.setVisibility(TextUtils.isEmpty(bottomSubTitle) ? GONE : VISIBLE);

        if (mIcon != -1) binding.ivImage.setImageResource(mIcon);

        if (tintDrawable) {
            binding.ivImage.setImageDrawable(ColorUtils.tintDrawable(PalmUtils.getDrawableCompact(mIcon),
                    PalmUtils.getColorCompact(R.color.light_theme_empty_icon_tint_color)));
        }
    }

    public void setTitle(String title) {
        binding.tvBottomTitle.setText(title);
        binding.tvBottomTitle.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
    }

    public void setSubTitle(String subTitle) {
        binding.tvBottomSubTitle.setText(subTitle);
        binding.tvBottomSubTitle.setVisibility(TextUtils.isEmpty(subTitle) ? GONE : VISIBLE);
    }

    public void setIcon(@DrawableRes int mIcon) {
        if (!tintDrawable) binding.ivImage.setImageResource(mIcon);
        else {
            binding.ivImage.setImageDrawable(ColorUtils.tintDrawable(PalmUtils.getDrawableCompact(mIcon),
                    PalmUtils.getColorCompact(R.color.light_theme_empty_icon_tint_color)));
        }
    }

    public void setIcon(Drawable drawable) {
        binding.ivImage.setImageDrawable(tintDrawable ?
                ColorUtils.tintDrawable(drawable, PalmUtils.getColorCompact(R.color.light_theme_empty_icon_tint_color)) : drawable);
    }

    public void useDarkTheme() {
        binding.tvBottomTitle.setTextColor(getResources().getColor(R.color.dark_theme_empty_text_color));
        binding.tvBottomSubTitle.setTextColor(getResources().getColor(R.color.dark_theme_empty_sub_text_color));
        if (mIcon != -1) {
            binding.ivImage.setImageDrawable(ColorUtils.tintDrawable(PalmUtils.getDrawableCompact(mIcon),
                    PalmUtils.getColorCompact(R.color.dark_theme_empty_icon_tint_color)));
        }
    }
}
