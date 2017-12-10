package xiaogao.zjut.tabbaishuo.views.tags;


import android.content.Context;
import android.util.AttributeSet;

import xiaogao.zjut.tabbaishuo.R;

public class TagView extends android.support.v7.widget.AppCompatTextView {

	private boolean mCheckEnable = true;

	public TagView(Context paramContext) {
		super(paramContext);
		init();
	}

	public TagView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init();
	}

	public TagView(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, 0);
		init();
	}

	private void init() {
		setText("");
		setBackgroundResource(R.drawable.tag_bg);

	}

	public void setCheckEnable(boolean paramBoolean) {
	}

	public void setChecked(boolean paramBoolean) {
	}
}
