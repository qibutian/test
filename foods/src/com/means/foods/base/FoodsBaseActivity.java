����   2 �  Ccom/gongpingjia/carplay/view/wheel/adapter/AbstractWheelTextAdapter  ?com/gongpingjia/carplay/view/wheel/adapter/AbstractWheelAdapter TEXT_VIEW_ITEM_RESOURCE I ConstantValue���� NO_RESOURCE     DEFAULT_TEXT_COLOR�XXX LABEL_COLOR�p p DEFAULT_TEXT_SIZE    	textColor textSize context Landroid/content/Context; inflater Landroid/view/LayoutInflater; itemResourceId itemTextResourceId emptyItemResourceId <init> (Landroid/content/Context;)V Code
     (Landroid/content/Context;I)V LineNumberTable LocalVariableTable this ELcom/gongpingjia/carplay/view/wheel/adapter/AbstractWheelTextAdapter;
  %  & (Landroid/content/Context;II)V itemResource
  )  * ()V	  ,  	  .  	  0  	  2  	  4   6 layout_inflater
 8 : 9 android/content/Context ; < getSystemService &(Ljava/lang/String;)Ljava/lang/Object; > android/view/LayoutInflater	  @   itemTextResource getTextColor ()I setTextColor (I)V getTextSize setTextSize getItemResource setItemResource getItemTextResource setItemTextResource getEmptyItemResource	  N   setEmptyItemResource getItemText (I)Ljava/lang/CharSequence; getItem A(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
  U V C getItemsCount
  X Y Z getView .(ILandroid/view/ViewGroup;)Landroid/view/View;
  \ ] ^ getTextView /(Landroid/view/View;I)Landroid/widget/TextView;
  ` P Q b  
 d f e android/widget/TextView g h setText (Ljava/lang/CharSequence;)V
  j k l configureTextView (Landroid/widget/TextView;)V index convertView Landroid/view/View; parent Landroid/view/ViewGroup; textView Landroid/widget/TextView; text Ljava/lang/CharSequence; StackMapTable x java/lang/CharSequence getEmptyItem @(Landroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
 d | D E
 d ~  E 
setGravity
 d � G � (F)V	 � � � !android/text/TextUtils$TruncateAt � � END #Landroid/text/TextUtils$TruncateAt;
 d � � � setEllipsize &(Landroid/text/TextUtils$TruncateAt;)V
 d � � � 
setPadding (IIII)V
 d � � E setLines view
 � � � android/view/View � � findViewById (I)Landroid/view/View; � AbstractWheelAdapter � ,You must supply a resource ID for a TextView
 � � � android/util/Log � � e '(Ljava/lang/String;Ljava/lang/String;)I � java/lang/IllegalStateException � >AbstractWheelAdapter requires the resource ID to be a TextView
 � �  � *(Ljava/lang/String;Ljava/lang/Throwable;)V � java/lang/ClassCastException textResource Ljava/lang/ClassCastException;
 d �  
 = � � � inflate /(ILandroid/view/ViewGroup;Z)Landroid/view/View; resource 
SourceFile AbstractWheelTextAdapter.java InnerClasses � android/text/TextUtils 
TruncateAt!                	       
                                                                       ?     *+� �        
    F  G !        " #                J     *+� $�        
    S  T !         " #            '     &     �     -*� (*� +*� -*+� /*� 1*� 3*+5� 7� =� ?�        * 
   a  0 
 1  c  d  e  g ! h ) g , i !   *    - " #     -      - '     - A    B C     /     *� +�            q !        " #    D E     >     *� +�        
    {  | !        " #          F C     /     *� -�            � !        " #    G E     >     *� -�        
    �  � !        " #          H C     /     *� 1�            � !        " #    I E     >     *� 1�        
    �  � !        " #          J C     /     *� 3�            � !        " #    K E     >     *� 3�        
    �  � !        " #          L C     /     *� M�            � !        " #    O E     >     *� M�        
    �  � !        " #         P Q    R S     �     S� P*� T� H,� **� 1-� WM*,*� 3� [:� (*� _:� a:� c*� 1� 	*� i,��        6    �  �  �  � % � * � 1 � 6 � : � A � I � O � Q � !   >    S " #     S m     S n o    S p q  % , r s  1  t u  v    �  d w� �   y z     �     '+� **� M,� WL*� M� +� d� *+� d� i+�            �  �  �  �  � % � !        ' " #     ' n o    ' p q  v      k l     z     .+*� +� {+� }+*� -�� �+� �� �+� �+� ��            �  �     ( - !       . " #     . � s   ] ^     �     =N� +� d� +� dN� )� %+� �� dN� :��� �W� �Y�� ��-�   " % �      :        " ' ) + / 3 5 7 ;! !   4    = " #     = � o    = �    ; t s  '  � �  v    �  dB �  Y Z     �     1�     %����          �� dY*� /� ��*� ?,� ��           , . 0 &2 !        1 " #     1 �     1 p q  v      �    � �   
  � � �@                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            