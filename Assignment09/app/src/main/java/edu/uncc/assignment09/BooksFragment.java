package edu.uncc.assignment09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uncc.assignment09.databinding.BooksRowItemBinding;
import edu.uncc.assignment09.databinding.FragmentBooksBinding;

public class BooksFragment extends Fragment {
    private static final String ARG_PARAM_GENRE = "ARG_PARAM_GENRE";
    private String mGenre;

    FragmentBooksBinding binding;
    ArrayList<Book> mBooks = new ArrayList<>();
    BooksListener mListener;
    BookAdapter mAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;


    public static BooksFragment newInstance(String genre) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGenre = getArguments().getString(ARG_PARAM_GENRE);
        }
    }


    public BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Books");
        mBooks.clear();
        mBooks.addAll(Data.getBooksByGenre(mGenre));
        mAdapter = new BookAdapter();
        mRecyclerView = binding.booksRecyclerView;
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        binding.buttonBack.setOnClickListener(v -> {
            mListener.closeBooks();
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BooksListener) {
            mListener = (BooksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BooksListener");
        }
    }

    interface BooksListener {
        void closeBooks();

        void gotoBookDetails(Book book);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitleTextView;
        TextView authorNameTextView;
        TextView genreTextView;
        TextView yearTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            BooksRowItemBinding binding = BooksRowItemBinding.bind(itemView);
            bookTitleTextView = binding.bookTitleTextView;
            authorNameTextView = binding.authorNameTextView;
            genreTextView = binding.genreTextView;
            yearTextView = binding.yearTextView;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.gotoBookDetails(mBooks.get(getAdapterPosition()));
                }
            });

        }
    }

    public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.books_row_item, parent, false);
            return new BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.authorNameTextView.setText(book.getAuthor());
            holder.bookTitleTextView.setText(book.getTitle());
            holder.genreTextView.setText(book.getGenre());
            holder.yearTextView.setText(String.valueOf(book.getYear()));
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }
    }
}