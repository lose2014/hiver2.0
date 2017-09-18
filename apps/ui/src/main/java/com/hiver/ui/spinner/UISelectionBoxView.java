package com.hiver.ui.spinner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hiver.ui.R;

/**
 * 选择控件，支持点击弹出单选框
 * 
 * @author changtao
 * 
 */
public class UISelectionBoxView extends TextView implements
		OnClickListener {
	/**
	 * 可选项
	 */
	private CharSequence[] entries;

	/**
	 * 可选项对应的值
	 */
	private String[] values;

	/**
	 * 可选项选中索引
	 */
	private int selectedIndex = -1;

	/**
	 * 选中内容对应的值
	 */
	private String selectedValue;

	/**
	 * 选择项变更通知
	 */
	private SelectionChangedListener listener;

	public UISelectionBoxView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray mArray = context.obtainStyledAttributes(attrs, R.styleable.ui_view_selection_box);

		int entriesResourceID = mArray.getResourceId(
				R.styleable.ui_view_selection_box_entries, -1);
		if (-1 != entriesResourceID) {
			entries = getResources().getStringArray(entriesResourceID);
		}

		int valuesResourceID = mArray.getResourceId(
				R.styleable.ui_view_selection_box_values, -1);
		if (-1 != valuesResourceID) {
			values = getResources().getStringArray(valuesResourceID);
			if (null != values && entries.length != values.length) {
				mArray.recycle();
				throw new RuntimeException("entries 和 values的length必须相同！");
			}
		}

		mArray.recycle();
	}

	public UISelectionBoxView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray mArray = context.obtainStyledAttributes(attrs,
				R.styleable.ui_view_selection_box);

		int entriesResourceID = mArray.getResourceId(
				R.styleable.ui_view_selection_box_entries, -1);
		if (-1 != entriesResourceID) {
			entries = getResources().getStringArray(entriesResourceID);
		}

		int valuesResourceID = mArray.getResourceId(
				R.styleable.ui_view_selection_box_values, -1);
		if (-1 != valuesResourceID) {
			values = getResources().getStringArray(valuesResourceID);
			if (null != values && entries.length != values.length) {
				mArray.recycle();
				throw new RuntimeException("entries 和 values的length必须相同！");
			}
		}

		mArray.recycle();
	}

	private UISelectionBoxView(Context context) {
		super(context);
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();

		if (null != entries && selectedIndex < entries.length) {
			this.selectedIndex = 0;
			if (null != values && selectedIndex < values.length) {
				this.selectedValue = values[selectedIndex];
			}
			
			setText(entries[selectedIndex]);
			
		}

		this.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// 点击后，弹出选择框
		if (!v.isEnabled()) {
			return;
		}
		if (null == entries || 0 == entries.length) {
			// 如果未设置选项
			return;
		}
		new AlertDialog.Builder(getContext()).setSingleChoiceItems(entries,
				selectedIndex, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setSelectedIndex(which);
						dialog.dismiss();
						dialog = null;
					}
				}).show();
	}

	/**
	 * 设置选择项变更监听对象
	 * 
	 * @param listener
	 */
	public void setListener(SelectionChangedListener listener) {
		this.listener = listener;
	}

	/**
	 * 设置选项
	 * 
	 * @param entries
	 *            选项集合
	 */
	public void setEntries(CharSequence[] entries) {
		this.entries = entries;
		if (null != entries && entries.length > 0) {
			// 还原索引，保证每次设置数据源时，刷新View的信息
			this.selectedIndex = -1;
			setSelectedIndex(0);
		}
	}
	
	/**
	 * 设置选项
	 * 
	 * @param entries
	 *            选项集合
	 */
	public void setEntries(CharSequence[] entries ,int index) {
		this.entries = entries;
		if (null != entries && entries.length > 0) {
			// 还原索引，保证每次设置数据源时，刷新View的信息
			if (-1 != index) {
				setSelectedIndex(index);
			} else {
				this.selectedIndex = -1;
				setSelectedIndex(0);
			}
		}
	}

	/**
	 * 设置选中索引
	 * 
	 * @param selectedIndex
	 *            选择的索引
	 */
	public void setSelectedIndex(int selectedIndex) {
		if (this.selectedIndex == selectedIndex) {
			// 如果选择项未变更，则什么也不做
			return;
		}
		this.selectedIndex = selectedIndex;
		setText(entries[selectedIndex]);
		if (null != values) {
			selectedValue = values[selectedIndex];
		}
		if (null != listener) {
			listener.onSelectionChanged(getId(),selectedIndex);
		}
	}

	/**
	 * 获取选中的索引
	 * 
	 * @return 索引
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * 获取选中内容对应的值
	 * 
	 * @return 值
	 */
	public String getSelectedValue() {
		if (null != values && this.selectedIndex < values.length) {
			return values[this.selectedIndex];
		}
		return "";
	}

	/**
	 * 获取选择的信息
	 * 
	 * @return
	 */
	public CharSequence getSelectedInfo() {
		if (selectedIndex >= 0 && selectedIndex < entries.length) {
			return entries[selectedIndex];
		}
		return null;
	}

	@Override
	public Parcelable onSaveInstanceState() {
		// TODO Auto-generated method stub
		Parcelable pl = super.onSaveInstanceState();
		SaveState st = new SaveState(pl);
		st.index = this.selectedIndex;
		st.value = this.selectedValue;

		return st;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		// TODO Auto-generated method stub
		SaveState st = (SaveState) state;
		super.onRestoreInstanceState(st.getSuperState());
		if (null == entries) {
			// 如果数据源为空，则不还原本地数据
			return;
		}

		this.selectedValue = st.value;
		if (st.index >= 0 && st.index < entries.length) {
			this.setSelectedIndex(st.index);
		}

	}

	protected static class SaveState extends BaseSavedState {
		int index;
		String value;

		public SaveState(Parcelable pl) {
			super(pl);
		}

		public SaveState(Parcel in) {
			super(in);

			index = in.readInt();
			value = in.readString();
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(index);
			dest.writeString(value);
		}

		public static final Creator<SaveState> CREATOR = new Creator<SaveState>() {
			@Override
			public SaveState createFromParcel(Parcel in) {
				return new SaveState(in);
			}

			@Override
			public SaveState[] newArray(int size) {
				return new SaveState[size];
			}
		};
	}

	/**
	 * 选择项变更监听
	 * 
	 * @author changtao
	 * 
	 */
	public interface SelectionChangedListener {
		/**
		 * 选择项变更通知
		 * 
		 * @param id
		 *            view编号
		 * @param selectedIndex
		 *            选中索引
		 */
		public void onSelectionChanged(int id, int selectedIndex);
	}
}
