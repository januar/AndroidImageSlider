package com.daimajia.slider.library;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.RenderTypes.BaseSliderView;

import java.util.ArrayList;

/**
 * Created by daimajia on 14-5-27.
 */
public class SliderAdapter extends PagerAdapter implements BaseSliderView.ImageLoadListener{

    private Context mContext;
    private ArrayList<BaseSliderView> mImageContents;


    public SliderAdapter(Context context){
        mContext = context;
        mImageContents = new ArrayList<BaseSliderView>();
    }

    public <T extends BaseSliderView> void addSlider(T slider){
        slider.setOnImageLoadListener(this);
        mImageContents.add(slider);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public <T extends BaseSliderView> void removeSlider(T slider){
        if(mImageContents.contains(slider)){
            mImageContents.remove(slider);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mImageContents.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseSliderView image = mImageContents.get(position);
        View v = image.getView();
        container.addView(v);
        return v;
    }

    @Override
    public void onStart(BaseSliderView target) {

    }

    /**
     * When image download error, then remove.
     * @param result
     * @param target
     */
    @Override
    public void onEnd(boolean result, BaseSliderView target) {
        if(target.isErrorDisappear() == false || result == true){
            return;
        }
        for (BaseSliderView slider: mImageContents){
            if(slider.equals(target)){
                removeSlider(target);
                break;
            }
        }
    }

}