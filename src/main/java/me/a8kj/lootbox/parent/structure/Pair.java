package me.a8kj.lootbox.parent.structure;

import java.util.Objects;

import lombok.NonNull;

/**
 * Data class for holding a pair of non-null objects with generic types.
 *
 * @param <L> Left generic type argument
 * @param <R> Right generic type argument
 */
public final class Pair<L, R> {
    private final L left;
    private final R right;

    private Pair(final @NonNull L left, final @NonNull R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Get the left value
     *
     * @return Left value
     */
    public @NonNull L left() {
        return this.left;
    }

    /**
     * Get the right value
     *
     * @return Right value
     */
    public @NonNull R right() {
        return this.right;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Pair<?, ?> pair = (Pair<?, ?>) o;
        return this.left.equals(pair.left) && this.right.equals(pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.left, this.right);
    }

    /**
     * Create a new Pair
     *
     * @param left  Left value
     * @param right Right value
     * @param <L>   Left generic type
     * @param <R>   Right generic type
     * @return new pair
     */
    public static <L, R> Pair<L, R> of(final @NonNull L left, final @NonNull R right) {
        return new Pair<>(left, right);
    }
}