package com.taikang.tkdoctor.fragment.selfdiagnosis;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.customview.ButtonM;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

	@SuppressLint("ResourceAsColor") 
	public class BodySelect implements OnClickListener {
	
	private Context context;
	private int parentWidth, parentHeight;
	private int deviceWidth,deviceHeight;
	private BodySelectCallback callback;
	public int currentState = 0;
	
	private RelativeLayout parentView;
	private LinearLayout zuxianView;
	private ImageView mIvHead,mIvNeck,mIvHand,mIvTorso,mIvOrgan,mIvHip,mIvLeg;
	private ButtonM btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
	
    public BodySelect(Context context, LinearLayout ZuxianView, int width, int height,BodySelectCallback callback)  
    {  
        this.context = context;
        this.parentWidth = width;
        this.parentHeight = height;
        this.callback = callback;
        this.zuxianView = ZuxianView;
        
		initView();
    }
    
    private void initView() {
    	parentView = (RelativeLayout) zuxianView.findViewById(R.id.rl_zizhen_renti);
        mIvHead = (ImageView) zuxianView.findViewById(R.id.iv_zizhen_head);
		mIvNeck = (ImageView) zuxianView.findViewById(R.id.iv_zizhen_neck);
		mIvHand = (ImageView) zuxianView.findViewById(R.id.iv_zizhen_hand);
		mIvTorso = (ImageView) zuxianView.findViewById(R.id.iv_zizhen_torso);
		mIvOrgan = (ImageView) zuxianView.findViewById(R.id.iv_zizhen_organ);
		mIvHip = (ImageView) zuxianView.findViewById(R.id.iv_zizhen_hip);
		mIvLeg = (ImageView) zuxianView.findViewById(R.id.iv_zizhen_leg);
		
		mIvHead.setOnClickListener(this);
		mIvNeck.setOnClickListener(this);
		mIvHand.setOnClickListener(this);
		mIvTorso.setOnClickListener(this);
		mIvOrgan.setOnClickListener(this);
		mIvHip.setOnClickListener(this);
		mIvLeg.setOnClickListener(this);
	}

	/**
     * 根据选择加载不同Button控件
     * 1：manFront 2:manBack 3:womenFront 4:womenBack
     * @param type
     */
    public void loadView(int type, int deviceWidth, int deviceHeight) {
    	this.deviceWidth = deviceWidth;
    	this.deviceHeight = deviceHeight;
    	currentState = type;
		switch (currentState) {
		case 1:
			manFrontSelect();
			break;
		case 2:
			manBackSelect();
			break;
		case 3:
			womenFrontSelect();
			break;
		case 4:
			womenBackSelect();
			break;
		default:
			break;
		}
	}
    
    //男士正面
  	private void manFrontSelect() {
  		initButton(1);
  		mIvHip.setEnabled(false);
  	}
  	
  	//男士背面
  	private void manBackSelect() {
  		// TODO Auto-generated method stub
  		initButton(2);
  		mIvOrgan.setEnabled(false);
  	}
  	
  	//女士正面
  	private void womenFrontSelect() {
  		// TODO Auto-generated method stub
  		initButton(3);
  		mIvHip.setEnabled(false);
  	}
  	
    //女士背面
	private void womenBackSelect() {
		// TODO Auto-generated method stub
		initButton(4);
  		mIvOrgan.setEnabled(false);
	}
	
	private void initButton(int i){
		//头部button
  		btn1 = new ButtonM(context);
		btn1.setFillet(true);
	    //设置圆角的半径大小
		btn1.setRadius(150);
//		btn1.setBackColor(R.color.weight_blue);
//        btn1.setText("头");
        btn1.setId(1); 
        btn1.setOnClickListener(this);
         
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);  
        lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);  
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        lp1.topMargin = (int) (parentHeight/100*3);
        lp1.width = (int) (parentWidth/100*29);
        lp1.height = (int) (parentHeight/100*13.5);
        // btn1 位于父 View 的顶部，在父 View 中水平居中  
        parentView.addView(btn1, lp1 );
        
        //颈部button
        btn2 = new ButtonM(context);
//        btn2.setBackColor(R.color.weight_blue);
//        btn2.setText("颈");  
        btn2.setId(2);  
        btn2.setOnClickListener(this);
         
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);  
        lp2.addRule(RelativeLayout.BELOW, 1);  
        lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);  
        lp2.width = (int) (parentWidth/100*24);
        lp2.height = (int) (parentHeight/100*4);
        // btn2 位于 btn1 的下方，在父 Veiw 中水平居中  
        parentView.addView(btn2,lp2);
        
        //腹部
        btn3 = new ButtonM(context);
//        btn3.setBackColor(R.color.weight_blue);
//        btn3.setText("腹");  
        btn3.setId(3);  
        btn3.setOnClickListener(this);
         
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);  
        lp3.addRule(RelativeLayout.BELOW, 2);  
        lp3.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);  
        lp3.width = (int) (parentWidth/100*48);
        lp3.height = (int) (parentHeight/100*27);
        // btn3 位于 btn2 的下方，在父 Veiw 中水平居中  
        parentView.addView(btn3,lp3);
        
        //生殖or臀部
        btn4 = new ButtonM(context);
//        btn4.setBackColor(R.color.color_63);
//        btn4.setText("殖");
//        if (i == 1 || i ==3) {
        	btn4.setId(4);
//		}else {
//			btn4.setId(8);
//		}  
        btn4.setOnClickListener(this);
         
        RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);  
        lp4.addRule(RelativeLayout.BELOW, 3);  
        lp4.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);  
        lp4.width = (int) (parentWidth/100*52);
        lp4.height = (int) (parentHeight/100*7.5);
        // btn4 位于 btn3 的下方，在父 Veiw 中水平居中  
        parentView.addView(btn4,lp4);
        
        //腿部
        btn5 = new ButtonM(context);
//        btn5.setBackColor(R.color.weight_blue);
//        btn5.setText("腿");  
        btn5.setId(5);  
        btn5.setOnClickListener(this);
         
        RelativeLayout.LayoutParams lp5 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);  
        lp5.addRule(RelativeLayout.BELOW, 4);  
        lp5.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);  
        lp5.width = (int) (parentWidth/100*50);
        lp5.height = (int) (parentHeight/100*47.5);
        // btn5 位于 btn4 的下方，在父 Veiw 中水平居中  
        parentView.addView(btn5,lp5);
        
        //左胳膊
        btn6 = new ButtonM(context);
        btn1.setFillet(true);
	    //设置圆角的半径大小
		btn1.setRadius(50);
//        btn6.setBackColor(R.color.weight_blue);
//        btn6.setText("左");  
        btn6.setId(6);  
        btn6.setOnClickListener(this);
         
        RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);  
        lp6.addRule(RelativeLayout.LEFT_OF, 4);  
        lp6.addRule(RelativeLayout.ALIGN_TOP, 3);  
        lp6.width = (int) (parentWidth/100*16);
        lp6.height = (int) (parentHeight/100*40);
        // btn6 位于 btn4 的左侧，与btn3水平居中  
        parentView.addView(btn6,lp6);
        
        //右胳膊
        btn7 = new ButtonM(context);
        btn1.setFillet(true);
	    //设置圆角的半径大小
		btn1.setRadius(50);
//        btn7.setBackColor(R.color.weight_blue);
//        btn7.setText("右");  
        btn7.setId(7);  
        btn7.setOnClickListener(this);
         
        RelativeLayout.LayoutParams lp7 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);  
        lp7.addRule(RelativeLayout.RIGHT_OF, 4);  
        lp7.addRule(RelativeLayout.ALIGN_TOP, 3);  
        lp7.width = (int) (parentWidth/100*16);
        lp7.height = (int) (parentHeight/100*40);
        // btn7 位于 btn4 的右侧，与btn3水平居中 
        parentView.addView(btn7,lp7);
	}
	
	public void removeSelect() {
		mIvHead.setImageResource(R.drawable.zizhen_head_default);
		mIvNeck.setImageResource(R.drawable.zizhen_neck_default);
		mIvHand.setImageResource(R.drawable.zizhen_hand_default);
		mIvTorso.setImageResource(R.drawable.zizhen_torso_default);
		mIvOrgan.setImageResource(R.drawable.zizhen_organ_default);
		mIvHip.setImageResource(R.drawable.zizhen_hip_default);
		mIvLeg.setImageResource(R.drawable.zizhen_leg_default);
	}
	
	public void removeAllSelect() {
		removeSelect();
		if (currentState == 1) {
			parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_default));
		}else if (currentState == 2) {
			parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_default));
		}else if (currentState == 3) {
			parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_default));
		}else {
			parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_default));
		}
	}
	
	public void modifyBody(int i){
		removeSelect();
		currentState = i;
//		parentView.removeAllViews();
//		loadView(i, parentWidth, parentHeight);
		View rootView = (View) parentView.getParent().getParent();
		switch (i) {
		case 2:
			parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_default));
			rootView.setBackground(context.getResources().getDrawable(R.drawable.bg_zizhen_man));
			mIvOrgan.setEnabled(false);
			mIvHip.setEnabled(true);
			break;
		case 1:
			mIvOrgan.setEnabled(true);
			mIvHip.setEnabled(false);
			parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_default));
			rootView.setBackground(context.getResources().getDrawable(R.drawable.bg_zizhen_man));
			break;	
		case 4:
			mIvOrgan.setEnabled(false);
			mIvHip.setEnabled(true);
			parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_default));
			rootView.setBackground(context.getResources().getDrawable(R.drawable.bg_zizhen_woman));
			break;
		case 3:
			mIvOrgan.setEnabled(true);
			mIvHip.setEnabled(false);
			parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_default));
			rootView.setBackground(context.getResources().getDrawable(R.drawable.bg_zizhen_woman));
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int aa = v.getId();
		LogUtils.e(aa + "");
		switch (v.getId()) {
		case 1:
			removeSelect();
			mIvHead.setImageResource(R.drawable.zizhen_head_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_head));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_head));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_head));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_head));
			}
			callback.selector(1);//选择头部
			break;
		case 2:
			removeSelect();
			mIvNeck.setImageResource(R.drawable.zizhen_neck_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_neck));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_neck));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_neck));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_neck));
			}
			callback.selector(2);//选择颈部
			break;
		case 3:
			removeSelect();
			mIvTorso.setImageResource(R.drawable.zizhen_torso_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_torso));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_torso));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_torso));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_torso));
			}
			callback.selector(3);//选择躯干
			break;
		case 4:
			removeSelect();
			if (currentState == 1 || currentState == 3) {
				mIvOrgan.setImageResource(R.drawable.zizhen_organ_selected);
				if (currentState == 1) {
					parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_organ));
				}else if (currentState == 2) {
					
				}else if (currentState == 3) {
					parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_organ));
				}else {
					
				}
			}else {
				mIvHip.setImageResource(R.drawable.zizhen_hip_selected);
				if (currentState == 1) {
					
				}else if (currentState == 2) {
					parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_hip));
				}else if (currentState == 3) {
					
				}else {
					parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_hip));
				}
			}
			callback.selector(4);//选择生殖or臀部
			break;
		case 5:
			removeSelect();
			mIvLeg.setImageResource(R.drawable.zizhen_leg_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_leg));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_leg));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_leg));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_leg));
			}
			callback.selector(5);//选择腿部
			break;
		case 6:
			removeSelect();
			mIvHand.setImageResource(R.drawable.zizhen_hand_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_hand));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_hand));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_hand));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_hand));
			}
			callback.selector(6);//选择胳膊
			break;
		case 7:
			removeSelect();
			mIvHand.setImageResource(R.drawable.zizhen_hand_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_hand));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_hand));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_hand));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_hand));
			}
			callback.selector(6);//选择胳膊
			break;
		case R.id.iv_zizhen_head:
			btn1.performClick();
			break;
		case R.id.iv_zizhen_neck:
			btn2.performClick();
			break;
		case R.id.iv_zizhen_hand:
			btn6.performClick();
			btn7.performClick();
			break;
		case R.id.iv_zizhen_torso:
			btn3.performClick();
			break;
		case R.id.iv_zizhen_organ:
			btn4.performClick();
			break;
		case R.id.iv_zizhen_leg:
			btn5.performClick();
			break;
		case R.id.iv_zizhen_hip:
			btn4.performClick();
			break;
		default:
			break;
		}
	}

	public void selector(int part) {
		removeAllSelect();
		switch (part) {
		case 0:
			
			break;
		case 1://头部
			mIvHead.setImageResource(R.drawable.zizhen_head_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_head));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_head));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_head));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_head));
			}	
			break;
		case 2://颈部
			mIvNeck.setImageResource(R.drawable.zizhen_neck_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_neck));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_neck));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_neck));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_neck));
			}
			break;
		case 3://手臂
			mIvHand.setImageResource(R.drawable.zizhen_hand_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_hand));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_hand));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_hand));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_hand));
			}

			break;
		case 4://躯干
			mIvTorso.setImageResource(R.drawable.zizhen_torso_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_torso));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_torso));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_torso));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_torso));
			}
			break;
		case 5://生殖器官
			mIvOrgan.setImageResource(R.drawable.zizhen_organ_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_organ));
			}else if (currentState == 2){
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_organ));
			}
			else if (currentState == 3){
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_organ));
			}else if (currentState == 4){
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_organ));
			}
			break;
		case 7://腿部
			mIvLeg.setImageResource(R.drawable.zizhen_leg_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manfront_leg));
			}else if (currentState == 2) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_leg));
			}else if (currentState == 3) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanfront_leg));
			}else {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_leg));
			}
			break;
		case 6://臀部
			mIvHip.setImageResource(R.drawable.zizhen_hip_selected);
			if (currentState == 1) {
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_hip));
			}else if (currentState == 2){
				parentView.setBackground(context.getResources().getDrawable(R.drawable.manback_hip));
			}else if (currentState == 3){
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_hip));
			}else if (currentState == 4){
				parentView.setBackground(context.getResources().getDrawable(R.drawable.womanback_hip));
			}
			break;

		default:
			break;
		}
	}

}
