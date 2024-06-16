package edu.uncc.assignment09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment09.databinding.FragmentBookDetailsBinding;


public class BookDetailsFragment extends Fragment {
    private static final String ARG_PARAM_BOOK = "ARG_PARAM_BOOK";

    private Book mBook;
    FragmentBookDetailsBinding binding;

    BooksDetailsListener mListener;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBook = (Book) getArguments().getSerializable(ARG_PARAM_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Book Details");
        binding.textViewBookTitle.setText(mBook.getTitle());
        binding.textViewAuthorName.setText(mBook.getAuthor());
        binding.textViewGenre.setText(mBook.getGenre());
        binding.textViewYear.setText(String.valueOf(mBook.getYear()));
        binding.buttonBack.setOnClickListener(v -> mListener.closeBookDetails());
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BooksDetailsListener) {
            mListener = (BooksDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BooksDetailsListener");
        }
    }

    interface BooksDetailsListener {
        void closeBookDetails();
    }
}