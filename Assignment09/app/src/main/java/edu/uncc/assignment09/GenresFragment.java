package edu.uncc.assignment09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uncc.assignment09.databinding.FragmentGenresBinding;
import edu.uncc.assignment09.databinding.GenresRowItemBinding;

public class GenresFragment extends Fragment {
    public GenresFragment() {
        // Required empty public constructor
    }

    FragmentGenresBinding binding;
    ArrayList<String> mGenres = Data.getAllGenres();

    GenresListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGenresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Genres");
        binding.genresList.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        binding.genresList.setAdapter(new GenresAdapter());
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GenresListener) {
            mListener = (GenresListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GenresListener");
        }
    }

    interface GenresListener {
        void gotoBooksForGenre(String genre);
    }

    public class GenresViewHolder extends RecyclerView.ViewHolder {
        TextView genreTextView;
        GenresRowItemBinding binding;

        public GenresViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = GenresRowItemBinding.bind(itemView);
            genreTextView = binding.textViewGenre;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.gotoBooksForGenre(mGenres.get(getAdapterPosition()));
                }
            });
        }
    }

    public class GenresAdapter extends RecyclerView.Adapter<GenresViewHolder> {
        @NonNull
        @Override
        public GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GenresRowItemBinding binding = GenresRowItemBinding.inflate(getLayoutInflater(), parent, false);
            View view = binding.getRoot();
            return new GenresViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GenresViewHolder holder, int position) {
            holder.genreTextView.setText(mGenres.get(position));

        }

        @Override
        public int getItemCount() {
            return mGenres.size();
        }
    }
}
