package goodyapp.example.com.goodyapp;

import android.support.annotation.NonNull;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import goodyapp.example.com.goodyapp.presentation.views.activities.GoodsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class GoodsActivityTest {

    private static final int RECYCLER_ITEM_TO_CLICK = 1;

    @Mock Utils mockUtils;

    @Rule
    public ActivityTestRule<GoodsActivity> mActivityRule = new ActivityTestRule<>(
            GoodsActivity.class);
    private String expectedQuantityAfterAdding;
    private String expectedQuantity;

    @Before
    public void setUp() throws Exception {
        expectedQuantityAfterAdding = "1";
        expectedQuantity = "0";
    }

    @Test
    public void addQuantityAndRemoveQuantityToAGood() {

        //Adding good
        onView(withId(R.id.goods_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(RECYCLER_ITEM_TO_CLICK,
                        new ClickOnRecyclerItemView(R.id.add_good)));

        onView(withId(R.id.goods_recycler))
                .check(matches(atPosition(RECYCLER_ITEM_TO_CLICK, hasDescendant(withText(expectedQuantityAfterAdding)))));

        // Removing good
        onView(withId(R.id.goods_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(RECYCLER_ITEM_TO_CLICK,
                        new ClickOnRecyclerItemView(R.id.remove_good)));

        // Expected result after adding and removing -> 0
        onView(withId(R.id.goods_recycler))
                .check(matches(atPosition(RECYCLER_ITEM_TO_CLICK, hasDescendant(withText(expectedQuantity)))));

    }

    @Test
    public void RemoveQuantityToAGoodWithZeroQuantityRemainsZero() {

        // Removing good
        onView(withId(R.id.goods_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(RECYCLER_ITEM_TO_CLICK,
                        new ClickOnRecyclerItemView(R.id.remove_good)));

        onView(withId(R.id.goods_recycler))
                .check(matches(atPosition(RECYCLER_ITEM_TO_CLICK, hasDescendant(withText(expectedQuantity)))));

    }


    @Test
    public void AddGoodToBasketReinitializeTheQuantityToZero() {

        onView(withId(R.id.goods_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(RECYCLER_ITEM_TO_CLICK,
                        new ClickOnRecyclerItemView(R.id.add_good)));

        onView(withId(R.id.goods_recycler))
                .check(matches(atPosition(RECYCLER_ITEM_TO_CLICK, hasDescendant(withText(expectedQuantityAfterAdding)))));

        onView(withId(R.id.goods_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(RECYCLER_ITEM_TO_CLICK,
                        new ClickOnRecyclerItemView(R.id.remove_good)));

        onView(withId(R.id.goods_recycler))
                .check(matches(atPosition(RECYCLER_ITEM_TO_CLICK, hasDescendant(withText(expectedQuantity)))));
    }


    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    static class ClickOnRecyclerItemView implements ViewAction {
        private int viewID;
        ViewAction click = click();

        public ClickOnRecyclerItemView(int viewID){
            this.viewID = viewID;
        }

        @Override
        public Matcher<View> getConstraints() {
            return click.getConstraints();
        }

        @Override
        public String getDescription() {
            return " click on custom view";
        }

        @Override
        public void perform(UiController uiController, View view) {
            click.perform(uiController, view.findViewById(this.viewID));
        }
    }
}
