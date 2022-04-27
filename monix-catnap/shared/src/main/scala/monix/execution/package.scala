/*
 * Copyright (c) 2014-2019 by The Monix Project Developers.
 * See the project homepage at: https://monix.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package monix

import cats.Contravariant

import scala.concurrent.ExecutionContext

package object execution {

  /** Returns the associated Cats type class instances for the
    * [[CancelableFuture]] data type.
    *
    * @param ec is the
    *        [[scala.concurrent.ExecutionContext ExecutionContext]]
    *        needed in order to create the needed type class instances,
    *        since future transformations rely on an `ExecutionContext`
    *        passed explicitly (by means of an implicit parameter)
    *        on each operation
    */
  implicit def cancelableFutureCatsInstances(implicit ec: ExecutionContext): CancelableFutureCatsInstances =
    new CancelableFutureCatsInstances()

  /** Contravariant type class instance of [[Callback]] for Cats. */
  implicit def contravariantCallback[E]: Contravariant[Callback[E, *]] =
    contravariantRef.asInstanceOf[Contravariant[Callback[E, *]]]

  private[this] val contravariantRef: Contravariant[Callback[Any, *]] =
    new Contravariant[Callback[Any, *]] {
      override def contramap[A, B](cb: Callback[Any, A])(f: B => A): Callback[Any, B] =
        cb.contramap(f)
    }
}
