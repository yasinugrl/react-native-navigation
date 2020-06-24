#import "SharedElementTransitionsCreator.h"
#import "RNNElementFinder.h"
#import "AnimatedViewFactory.h"
#import "NSArray+utils.h"
#import "SharedElementAnimator.h"

@implementation SharedElementTransitionsCreator

+ (NSArray<DisplayLinkAnimatorDelegate>*)create:(NSArray<SharedElementTransitionOptions *>*)sharedElementTransitions
                                         fromVC:(UIViewController *)fromVC
                                           toVC:(UIViewController *)toVC {
    NSMutableArray<DisplayLinkAnimatorDelegate>* transitions = [NSMutableArray<DisplayLinkAnimatorDelegate> new];
    for (SharedElementTransitionOptions* transitionOptions in sharedElementTransitions) {
        UIView *fromView = [RNNElementFinder findElementForId:transitionOptions.fromId inView:fromVC.view];
        UIView *toView = [RNNElementFinder findElementForId:transitionOptions.toId inView:toVC.view];
        if (fromView && toView) {
            SharedElementAnimator* sharedElementAnimator = [[SharedElementAnimator alloc] initWithTransitionOptions:transitionOptions
                                                                                                           fromView:fromView
                                                                                                             toView:toView
                                                                                                             fromVC:fromVC
                                                                                                               toVC:toVC];
            [transitions addObject:sharedElementAnimator];
        }
    }
    
    return transitions;
}

@end
