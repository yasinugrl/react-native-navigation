#import <Foundation/Foundation.h>
#import "AnimatedReactView.h"
#import "DisplayLinkAnimatorDelegate.h"
#import "BaseAnimator.h"

@interface SharedElementTransitionsCreator : NSObject

+ (NSArray<DisplayLinkAnimatorDelegate>*)create:(NSArray<SharedElementTransitionOptions *>*)sharedElementTransitions
                                         fromVC:(UIViewController *)fromVC
                                           toVC:(UIViewController *)toVC;

@end
