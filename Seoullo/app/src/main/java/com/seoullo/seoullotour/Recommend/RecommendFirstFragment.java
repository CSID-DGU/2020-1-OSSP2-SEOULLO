package com.seoullo.seoullotour.Recommend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.seoullo.seoullotour.R;

public class RecommendFirstFragment extends Fragment {

    private static final String TAG = "RecommendFirstFragment";

    //widget
    private TextView mTitle;
    private TextView mVicinity;
    private ImageView mImage;
    private TextView mDesc;
    private TextView mAnotherDecs;
    private LinearLayout mScrollItems;

    //var
    private String API_KEY;

    //firebase
    private String UserId;
    private String ImageName;
    private String PhotoId;

    //init - google place api
    RecommendFirstFragment(String ref2, String ref3, String ref4) {
        this.UserId = ref2;
        this.ImageName = ref3;
        this.PhotoId = ref4;
    }

    public static String getApiKeyFromManifest(Context context) {
        String apiKey = null;

        try {
            String e = context.getPackageName();
            ApplicationInfo ai = context
                    .getPackageManager()
                    .getApplicationInfo(e, PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            if (bundle != null) {
                apiKey = bundle.getString("com.google.android.geo.API_KEY");
            }
        } catch (Exception var6) {
            Log.d(TAG, "Caught non-fatal exception while retrieving apiKey: " + var6);
        }

        return apiKey;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view;

        API_KEY = getApiKeyFromManifest(this.getContext());

        view = inflater.inflate(R.layout.layout_recommend_first, container, false);
        mTitle = (TextView) view.findViewById(R.id.recommend_title);
        mVicinity = (TextView) view.findViewById(R.id.recommend_vicinity);
        mImage = (ImageView) view.findViewById(R.id.recommend_image);
        mDesc = (TextView) view.findViewById(R.id.recommend_desc);
        mScrollItems = (LinearLayout) view.findViewById(R.id.scroll_type_item);

        //이미지
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://seoullo-4fbc1.appspot.com");
        storageReference.child("photos").child("users").child(UserId).child(ImageName)
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //TODO: request manager 사용하기
                        Glide.with(getContext())
                                .load(uri)
                                .into(mImage);
                        mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                });
        //나머지
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //vicinity
        Query vQuery = reference.child("photos").child(PhotoId).child("location");
        vQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String [] location = dataSnapshot.getValue().toString().split(" ");
                String trimmedLocation = "";
                for(int i=2; i<location.length;++i)
                    trimmedLocation += location[i] + " ";
                mVicinity.setText(trimmedLocation);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mVicinity.setText("not loaded!");
            }
        });
        //title
        mTitle.setText("선택하신 곳");
        Query dQuery = reference.child("photos").child(PhotoId).child("caption");
        dQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDesc.setText(dataSnapshot.getValue().toString());
                //progress bar end
                ProgressBar pg = getActivity().findViewById(R.id.progress_mainfeed_to_recommend);
                pg.setVisibility(View.GONE);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDesc.setText("not loaded!");
            }
        });

        for(int i=0; i<4; ++i) {
            TextView item = new TextView(this.getContext());

            switch (i) {
                case 0:
                    item.setText(Html.fromHtml("#이곳을기준으로3KM"));
                    break;
                case 1:
                    item.setText(Html.fromHtml("#사람들이관심지역으로등록된곳"));
                    break;
                case 2:
                    item.setText(Html.fromHtml("#오른쪽으로넘겨서볼수있답니"));
                    break;
                default:
                    item.setText(Html.fromHtml("#서울로장소추천!"));
            }
            item.setTextSize(10);
            item.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_shape));
            item.setMovementMethod(new ScrollingMovementMethod());
            item.setPadding(20, 0, 20, 0);
            mScrollItems.addView(item);
        }
        return view;
    }
}
