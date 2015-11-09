package israelbgf.gastei.mobile.presenters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import israelbgf.gastei.core.entities.ExpenseEntity;
import israelbgf.gastei.core.usecases.ListMonthlyExpensesUsecase.Presenter;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.actvities.ExpenseManagementActivity;

import java.text.DecimalFormat;
import java.util.*;

public class ListMonthlyExpensesUsecasePresenter implements Presenter {

    DecimalFormat CURRENCY_FORMATTER = new DecimalFormat("$#.##");

    private List<Map.Entry<Integer, List<ExpenseEntity>>> itens = new ArrayList<>();
    //    private ExpenseAdapter adapter;
    private ExpenseManagementActivity activity;

    public ListMonthlyExpensesUsecasePresenter(ExpenseManagementActivity activity) {
        this.activity = activity;
//        this.adapter = new ExpenseAdapter(activity, itens);
//        this.activity.setListAdapter(adapter);
    }

    @Override
    public void presentExpenses(Struct struct) {

        activity.setContentView(R.layout.expense_list);

        //Your RecyclerView
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Your RecyclerView.Adapter
        SimpleAdapter mAdapter = new SimpleAdapter(activity, new String[]{"duas", "coisa"});


        //This is the code to provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Section 1"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(1, "Section 2"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(2, "Section 3"));

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter sectionedAdapter = new SimpleSectionedRecyclerViewAdapter(activity, R.layout.expense_section, R.id.section_text, mAdapter);
        sectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        recyclerView.setAdapter(sectionedAdapter);


//        itens.clear();
//        itens.addAll(struct.dailyExpenses.entrySet());
//        adapter.notifyDataSetChanged();
    }

    public static class SimpleSectionedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final Context mContext;
        private static final int SECTION_TYPE = 0;

        private boolean mValid = true;
        private int mSectionResourceId;
        private int mTextResourceId;
        private LayoutInflater mLayoutInflater;
        private RecyclerView.Adapter mBaseAdapter;
        private SparseArray<Section> mSections = new SparseArray<Section>();


        public SimpleSectionedRecyclerViewAdapter(Context context, int sectionResourceId, int textResourceId,
                                                  RecyclerView.Adapter baseAdapter) {

            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mSectionResourceId = sectionResourceId;
            mTextResourceId = textResourceId;
            mBaseAdapter = baseAdapter;
            mContext = context;

            mBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    mValid = mBaseAdapter.getItemCount() > 0;
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    mValid = mBaseAdapter.getItemCount() > 0;
                    notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    mValid = mBaseAdapter.getItemCount() > 0;
                    notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    mValid = mBaseAdapter.getItemCount() > 0;
                    notifyItemRangeRemoved(positionStart, itemCount);
                }
            });
        }


        public static class SectionViewHolder extends RecyclerView.ViewHolder {

            public TextView title;

            public SectionViewHolder(View view, int mTextResourceid) {
                super(view);
                title = (TextView) view.findViewById(mTextResourceid);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView) {
            if (typeView == SECTION_TYPE) {
                final View view = LayoutInflater.from(mContext).inflate(mSectionResourceId, parent, false);
                return new SectionViewHolder(view, mTextResourceId);
            } else {
                return mBaseAdapter.onCreateViewHolder(parent, typeView - 1);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder sectionViewHolder, int position) {
            if (isSectionHeaderPosition(position)) {
                ((SectionViewHolder) sectionViewHolder).title.setText(mSections.get(position).title);
            } else {
                mBaseAdapter.onBindViewHolder(sectionViewHolder, sectionedPositionToPosition(position));
            }

        }

        @Override
        public int getItemViewType(int position) {
            return isSectionHeaderPosition(position)
                    ? SECTION_TYPE
                    : mBaseAdapter.getItemViewType(sectionedPositionToPosition(position)) + 1;
        }


        public static class Section {
            int firstPosition;
            int sectionedPosition;
            CharSequence title;

            public Section(int firstPosition, CharSequence title) {
                this.firstPosition = firstPosition;
                this.title = title;
            }

            public CharSequence getTitle() {
                return title;
            }
        }


        public void setSections(Section[] sections) {
            mSections.clear();

            Arrays.sort(sections, new Comparator<Section>() {
                @Override
                public int compare(Section o, Section o1) {
                    return (o.firstPosition == o1.firstPosition)
                            ? 0
                            : ((o.firstPosition < o1.firstPosition) ? -1 : 1);
                }
            });

            int offset = 0; // offset positions for the headers we're adding
            for (Section section : sections) {
                section.sectionedPosition = section.firstPosition + offset;
                mSections.append(section.sectionedPosition, section);
                ++offset;
            }

            notifyDataSetChanged();
        }

        public int positionToSectionedPosition(int position) {
            int offset = 0;
            for (int i = 0; i < mSections.size(); i++) {
                if (mSections.valueAt(i).firstPosition > position) {
                    break;
                }
                ++offset;
            }
            return position + offset;
        }

        public int sectionedPositionToPosition(int sectionedPosition) {
            if (isSectionHeaderPosition(sectionedPosition)) {
                return RecyclerView.NO_POSITION;
            }

            int offset = 0;
            for (int i = 0; i < mSections.size(); i++) {
                if (mSections.valueAt(i).sectionedPosition > sectionedPosition) {
                    break;
                }
                --offset;
            }
            return sectionedPosition + offset;
        }

        public boolean isSectionHeaderPosition(int position) {
            return mSections.get(position) != null;
        }


        @Override
        public long getItemId(int position) {
            return isSectionHeaderPosition(position)
                    ? Integer.MAX_VALUE - mSections.indexOfKey(position)
                    : mBaseAdapter.getItemId(sectionedPositionToPosition(position));
        }

        @Override
        public int getItemCount() {
            return (mValid ? mBaseAdapter.getItemCount() + mSections.size() : 0);
        }

    }

    public static class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

        private final Context mContext;
        private List<String> mData;

        public void add(String s, int position) {
            position = position == -1 ? getItemCount() : position;
            mData.add(position, s);
            notifyItemInserted(position);
        }

        public void remove(int position) {
            if (position < getItemCount()) {
                mData.remove(position);
                notifyItemRemoved(position);
            }
        }

        public static class SimpleViewHolder extends RecyclerView.ViewHolder {
            public final TextView title;

            public SimpleViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.day_of_week);
            }
        }

        public SimpleAdapter(Context context, String[] data) {
            mContext = context;
            if (data != null)
                mData = new ArrayList<String>(Arrays.asList(data));
            else mData = new ArrayList<String>();
        }

        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.expense_item, parent, false);
            return new SimpleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, final int position) {
            holder.title.setText(mData.get(position));
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Position =" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

}
